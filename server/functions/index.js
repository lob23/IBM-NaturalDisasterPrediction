// Fixed require statements
require("module-alias/register");

// Set up firebase admin
const functions = require("firebase-functions");
const admin = require("firebase-admin");
const serviceAccount = require("./serviceAccountKey.json");

const notificationInterval = 86400000;

require("dotenv").config();

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
});

// Import the express library
const express = require("express");
const cors = require("cors");
const {periodCheck} = require('./services/checkStorm')

// Route imports
const {userRoutes, stormRoutes} = require("@routes");

// Main App
const app = express();
app.use(cors({origin: true}));
app.use(express.json());

app.use((req, res, next) => {
  res.setHeader("Content-Type", "application/json");
  next();
});

// Function to be run periodically
function periodicallyNotification() {
  periodCheck();
}

setInterval(periodicallyNotification, notificationInterval);

// Base Route
app.get("/", (req, res) => {
  return res.status(200).send("Hi there!");
});

// User Routes
app.use("/user", userRoutes);
app.use("/weather", stormRoutes);

const port = 3000; // You can use any port you want
app.listen(port, () => {
  console.log(`Server is running on http://localhost:${port}`);
});

// Export the API to Firebase Cloud Functions
exports.app = functions.https.onRequest(app);
