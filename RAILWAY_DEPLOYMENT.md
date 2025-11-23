# Railway Deployment Guide 🚂

Complete step-by-step guide to deploy DexTor Backend to Railway.

## Prerequisites

- GitHub account
- Railway account (sign up with GitHub at [railway.app](https://railway.app))
- Your code pushed to GitHub

---

## Step 1: Create Railway Project

1. Go to [railway.app](https://railway.app)
2. Click **"Start a New Project"**
3. Select **"Deploy from GitHub repo"**
4. Select your **DexTor-backend** repository
5. Click **"Deploy Now"**

---

## Step 2: Add MySQL Database

1. In your Railway project, click **"+ New"**
2. Select **"Database"**
3. Choose **"Add MySQL"**
4. Railway will automatically create a MySQL database

---

## Step 3: Configure Environment Variables

Railway needs to know which Spring profile to use and how to connect to the database.

1. Click on your **Spring Boot service** (not the MySQL)
2. Go to **"Variables"** tab
3. Add these variables:

```
SPRING_PROFILES_ACTIVE=railway
DATABASE_URL=mysql://root:PASSWORD@containers-us-west-XXX.railway.app:3306/railway
PORT=8080
```

**Important:** Railway should auto-set `DATABASE_URL` when you add MySQL. If not:

1. Click on the **MySQL service**
2. Go to **"Connect"** tab
3. Copy the **"JDBC URL"**
4. Paste it as `DATABASE_URL` in your Spring Boot service

---

## Step 4: Build Configuration

Make sure these files are in your repository:

### `railway.json` ✅ (Already created)

Tells Railway how to build and run your app.

### `Procfile` ✅ (Already created)

Alternative way to tell Railway how to start the app.

### `application-railway.properties` ✅ (Already created)

Spring configuration for Railway environment.

---

## Step 5: Deploy

1. Railway will automatically deploy when you push to GitHub
2. Or manually trigger deployment:
   - Click on your service
   - Click **"Deploy"**
   - Select the branch to deploy

---

## Step 6: Get Your API URL

1. Click on your **Spring Boot service**
2. Go to **"Settings"** tab
3. Scroll to **"Networking"**
4. Click **"Generate Domain"**
5. You'll get a URL like: `https://your-app.up.railway.app`

---

## Step 7: Test Your API

Once deployed, test your endpoints:

```bash
# Replace with your Railway URL
curl https://your-app.up.railway.app/api/hello

curl https://your-app.up.railway.app/api/test/connection

curl -X POST "https://your-app.up.railway.app/api/test/create?message=Hello%20from%20Railway"

curl https://your-app.up.railway.app/api/test/all
```

---

## Troubleshooting Common Issues

### ❌ Build Failed

**Error:** "mvnw: not found" or "Permission denied"

**Solution:** Make sure `mvnw` is executable:

```bash
git update-index --chmod=+x mvnw
git add mvnw
git commit -m "Make mvnw executable"
git push
```

---

### ❌ Connection Refused / Database Error

**Problem:** App can't connect to MySQL

**Solution:**

1. Check `DATABASE_URL` is set correctly in Variables
2. Make sure it's the **JDBC URL** format:
   ```
   mysql://user:password@host:port/database
   ```
3. Check the MySQL service is running (green status)

---

### ❌ Port Binding Error

**Error:** "Port 8080 already in use"

**Solution:** Make sure your `application-railway.properties` has:

```properties
server.port=${PORT:8080}
```

Railway sets the `PORT` environment variable automatically.

---

### ❌ Application Crash Loop

**Problem:** App keeps restarting

**Solutions:**

1. **Check Logs:**

   - Click on your service
   - Go to "Logs" tab
   - Look for error messages

2. **Common Issues:**

   - Missing `SPRING_PROFILES_ACTIVE=railway` variable
   - Wrong `DATABASE_URL` format
   - Java version mismatch

3. **Force Java 21:**
   Add to Variables:
   ```
   NIXPACKS_JDK_VERSION=21
   ```

---

### ❌ 404 Not Found

**Problem:** URL works but endpoints return 404

**Solution:** Make sure you're using the correct path:

- ✅ `https://your-app.up.railway.app/api/hello`
- ❌ `https://your-app.up.railway.app/hello`

---

## Viewing Logs

1. Click on your Spring Boot service
2. Click **"Logs"** tab
3. You'll see real-time logs of your application

Look for:

- ✅ "Started TexDorApplication in X seconds"
- ✅ "Tomcat started on port"
- ❌ Any error messages

---

## Environment Variables Reference

Here are all the variables you might need:

```bash
# Required
SPRING_PROFILES_ACTIVE=railway
DATABASE_URL=mysql://root:password@host:port/railway

# Optional (Railway auto-sets these)
PORT=8080

# Optional (if you need specific Java version)
NIXPACKS_JDK_VERSION=21
```

---

## Update Deployment

To update your deployed app:

1. Make changes to your code
2. Commit and push to GitHub:
   ```bash
   git add .
   git commit -m "Your changes"
   git push
   ```
3. Railway automatically redeploys!

---

## Pricing (Free Tier)

Railway Free Tier includes:

- **$5 credit/month** (renewable)
- Usually enough for:
  - Small apps
  - Testing
  - Class projects
  - Low traffic APIs

**To stay within free tier:**

- Use the $5 credit wisely
- Remove the project when not needed
- Monitor usage in Railway dashboard

---

## Connect Teammates

Share your Railway URL with teammates:

1. Get your Railway URL (e.g., `https://your-app.up.railway.app`)
2. Share with team:

   ```
   API Base URL: https://your-app.up.railway.app

   Endpoints:
   - GET  /api/hello
   - GET  /api/test/connection
   - POST /api/test/create?message=<text>
   - GET  /api/test/all
   ```

Everyone can now use the same database!

---

## Local Development vs Railway

| Environment | Database      | URL                               |
| ----------- | ------------- | --------------------------------- |
| **Local**   | Your MySQL    | `http://localhost:8080`           |
| **Railway** | Railway MySQL | `https://your-app.up.railway.app` |

You can use both:

- **Local** for development and testing
- **Railway** for sharing with team

---

## Quick Commands

```bash
# Make mvnw executable
git update-index --chmod=+x mvnw

# Build locally to test
./mvnw clean package

# Run with Railway profile locally (for testing)
./mvnw spring-boot:run -Dspring-boot.run.profiles=railway

# View Railway CLI logs (if you install Railway CLI)
railway logs
```

---

## Success Checklist ✅

Before deployment, make sure:

- [ ] Code pushed to GitHub
- [ ] `mvnw` is executable
- [ ] `railway.json` exists
- [ ] `Procfile` exists
- [ ] `application-railway.properties` exists
- [ ] MySQL service added in Railway
- [ ] Environment variables set:
  - [ ] `SPRING_PROFILES_ACTIVE=railway`
  - [ ] `DATABASE_URL` set (auto or manual)
- [ ] Domain generated
- [ ] Logs show "Started TexDorApplication"

---

## Next Steps

Once deployed successfully:

1. ✅ Test all endpoints
2. ✅ Share URL with teammates
3. ✅ Update README with Railway URL
4. ✅ Monitor usage in Railway dashboard
5. ✅ Set up automatic deployments from main branch

---

## Support

If you still have issues:

1. Check Railway logs (most helpful!)
2. Check Railway documentation: https://docs.railway.app
3. Railway Discord: https://discord.gg/railway
4. Check your repository has all required files

---

## Summary

**Deployment Steps:**

1. Create Railway project from GitHub
2. Add MySQL database
3. Set environment variables
4. Generate domain
5. Test endpoints

**Key Files:**

- `railway.json` - Build configuration
- `Procfile` - Start command
- `application-railway.properties` - Railway-specific config

**That's it! Your API is now live and accessible to everyone!** 🚀
