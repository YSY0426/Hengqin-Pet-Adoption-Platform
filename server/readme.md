# Backend Documentation

## 🔧 Features

- Create, Read, Update, Delete operations  
- File upload  
- Database configuration  
- Logging management  
- Permission control  

---

## 🚀 Deployment Steps

1. **Configure application settings** in `application.yml`:  
   - Set the `port`  
   - Set the `DB_NAME`  
   - Set the `BASE_LOCATION`  

2. **Configure log file path** in `logback-spring.xml`:  
   - Set the value of `LOG_HOME`

3. **Build the project**:
   ```bash
   mvn clean
   mvn package
   ```

4. **Transfer the generated `.jar` file** to the server.

5. **Copy the `upload` folder** to the server (ensure correct file structure).

6. **Migrate the MySQL database** to the server.

7. **Start the application** by running the `.jar` file:
   ```bash
   java -jar -Xms64m -Xmx128m -XX:MetaspaceSize=64m -XX:MaxMetaspaceSize=64m your-app.jar
   ```

   Or, you can **double-click `start.sh`** if using a GUI-supported environment.

---

## 📄 Notes

- Ensure the MySQL database is already created and accessible.  
- Ports and paths must match server configuration.  
- The `upload` directory must be writable by the application.  
- Logs will be stored in the directory defined by `LOG_HOME`.
