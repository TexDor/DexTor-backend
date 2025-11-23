# Railway Deployment - Quick Fix Guide 🔧

Your deployment failed? Let's fix it! Follow these steps in order.

---

## Step 1: Check What Failed

Go to Railway → Your Project → Click on your service → **"Logs"** tab

Look for the error message. Common ones:

### Error: "mvnw: Permission denied" or "mvnw: not found"

**Fix:**

```bash
cd /Users/chinbatorchlon/Documents/school/DexTor-backend
git update-index --chmod=+x mvnw
git add mvnw
git commit -m "Fix mvnw permissions for Railway"
git push
```

---

### Error: "Could not find or load main class"

**Fix:** Make sure your `pom.xml` has the correct artifact ID. Check line 12-13:

```xml
<artifactId>inventory</artifactId>
<version>0.0.1-SNAPSHOT</version>
```

Then update `railway.json` and `Procfile` to match.

---

### Error: "Failed to connect to database" or "Access denied for user"

**Fix:**

1. In Railway, click on **MySQL service**
2. Go to **"Variables"** tab
3. Copy the `DATABASE_URL` value
4. Click on your **Spring Boot service**
5. Go to **"Variables"** tab
6. Make sure these are set:
   ```
   SPRING_PROFILES_ACTIVE=railway
   DATABASE_URL=<paste the MySQL URL here>
   ```

The DATABASE_URL should look like:

```
mysql://root:XXXXXXXXXXX@containers-us-west-123.railway.app:6379/railway
```

---

### Error: "Port already in use" or "Failed to bind to port"

**Fix:** Make sure `application-railway.properties` has:

```properties
server.port=${PORT:8080}
```

This lets Railway assign its own port.

---

### Error: "No compiler is provided in this environment"

**Fix:** Add Java version to Railway variables:

In your service → **Variables** tab → Add:

```
NIXPACKS_JDK_VERSION=21
```

---

## Step 2: Verify Required Files

Make sure these files exist in your repository:

```bash
# Check if files exist
ls -la railway.json
ls -la Procfile
ls -la src/main/resources/application-railway.properties
ls -la mvnw
```

All should show up. If any are missing, they're in your project now!

---

## Step 3: Commit and Push Everything

```bash
# Make sure mvnw is executable
git update-index --chmod=+x mvnw

# Add all files
git add .
git add railway.json
git add Procfile
git add src/main/resources/application-railway.properties
git add RAILWAY_DEPLOYMENT.md
git add RAILWAY_FIX.md

# Commit
git commit -m "Add Railway deployment configuration"

# Push
git push
```

---

## Step 4: Configure Railway Environment

1. Go to Railway → Your Project
2. Make sure you have **2 services**:
   - Your Spring Boot app
   - MySQL database

If you don't have MySQL:

- Click **"+ New"**
- Select **"Database"**
- Choose **"Add MySQL"**

---

## Step 5: Set Environment Variables

Click on your **Spring Boot service** (NOT MySQL) → **"Variables"** tab

Add these:

| Variable Name            | Value                                                          |
| ------------------------ | -------------------------------------------------------------- |
| `SPRING_PROFILES_ACTIVE` | `railway`                                                      |
| `DATABASE_URL`           | (Copy from MySQL service "Variables" tab - should auto-appear) |

**Important:** If `DATABASE_URL` doesn't appear automatically:

1. Click on **MySQL service**
2. Go to **"Connect"** tab
3. Look for **"JDBC URL"** or **"Connection String"**
4. Copy it
5. Go back to Spring Boot service → Variables → Add it manually

---

## Step 6: Generate Domain

1. Click on your **Spring Boot service**
2. Go to **"Settings"** tab
3. Scroll down to **"Networking"**
4. Click **"Generate Domain"**
5. You'll get: `https://something.up.railway.app`

---

## Step 7: Redeploy

After making changes:

1. Railway should auto-deploy when you push to GitHub
2. Or manually: Click service → Top right → **"Deploy"** → Select branch

---

## Step 8: Watch the Logs

1. Click on your service
2. Click **"Logs"** tab
3. Wait and watch for:

**✅ Success looks like:**

```
Started TexDorApplication in 15.234 seconds
Tomcat started on port(s): 8080 (http)
```

**❌ Failure looks like:**

```
Error: ...
Application run failed
Process exited with code 1
```

---

## Still Failing? Check These:

### 1. Java Version

Add to Variables:

```
NIXPACKS_JDK_VERSION=21
```

### 2. Build Command

Check `railway.json` has:

```json
{
  "build": {
    "builder": "NIXPACKS",
    "buildCommand": "./mvnw clean package -DskipTests"
  }
}
```

### 3. Start Command

Check `Procfile` has:

```
web: java -Dserver.port=$PORT $JAVA_OPTS -jar target/inventory-0.0.1-SNAPSHOT.jar
```

### 4. Application Properties

Check `application-railway.properties` has:

```properties
spring.datasource.url=${DATABASE_URL}
server.port=${PORT:8080}
```

---

## Test Your Deployment

Once it says "Started TexDorApplication", test it:

```bash
# Replace YOUR_RAILWAY_URL with your actual URL
curl https://YOUR_RAILWAY_URL.up.railway.app/api/hello

# Should return: Hello World from DexTor API!
```

---

## Common Mistakes Checklist

- [ ] `mvnw` is not executable → Run `git update-index --chmod=+x mvnw`
- [ ] Missing environment variables → Set `SPRING_PROFILES_ACTIVE=railway`
- [ ] Wrong DATABASE_URL format → Should start with `mysql://`
- [ ] Missing MySQL service → Add database in Railway
- [ ] Forgot to push files → `git push` your changes
- [ ] Using wrong profile → Must be `railway` not `local` or `docker`

---

## Quick Commands Reference

```bash
# Make mvnw executable
git update-index --chmod=+x mvnw

# Check if mvnw is executable
ls -la mvnw

# Test build locally
./mvnw clean package

# Add all files and push
git add .
git commit -m "Fix Railway deployment"
git push

# Test Railway API
curl https://your-app.up.railway.app/api/hello
```

---

## Need More Help?

1. **Check Railway Logs** (most important!)
   - Shows exactly what went wrong
2. **Read Full Guide:** [RAILWAY_DEPLOYMENT.md](./RAILWAY_DEPLOYMENT.md)

3. **Railway Docs:** https://docs.railway.app

4. **Railway Discord:** https://discord.gg/railway

---

## Success! ✅

If you see this in logs:

```
Started TexDorApplication in X seconds
Tomcat started on port(s): XXXX
```

**You're live!** 🎉

Test your API:

```bash
curl https://your-app.up.railway.app/api/hello
curl https://your-app.up.railway.app/api/test/connection
```

Share your URL with teammates and they can start using it!
