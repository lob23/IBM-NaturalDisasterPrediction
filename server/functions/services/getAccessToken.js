// Load environment variables from .env file
require('dotenv').config();

const fetch = require('node-fetch');
const fs = require('fs');
const jwt = require('jsonwebtoken'); // Import the jsonwebtoken package

// Define the URL for the token request
const url = 'https://iam.cloud.ibm.com/identity/token';

// Function to get the access token and save it to .env
async function getAccessToken() {
    const apiKey = process.env.API_KEY_IBM_Cloud; // Get API key from .env

    // Prepare the body as URL encoded form data
    const body = new URLSearchParams();
    body.append('grant_type', 'urn:ibm:params:oauth:grant-type:apikey');
    body.append('apikey', apiKey);

    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: body.toString(), // Convert the URLSearchParams to string
        });

        // Check if response is OK
        if (!response.ok) {
            throw new Error(`Error: ${response.statusText}`);
        }

        // Parse the response to JSON
        const data = await response.json();

        // Extract the access token
        const accessToken = data.access_token;
        console.log('Access Token:', accessToken);

        // Save the access token to the .env file
        saveAccessTokenToEnv(accessToken);

    } catch (error) {
        console.error('Error fetching access token:', error);
    }
}

// Function to save the access token into the .env file
function saveAccessTokenToEnv(accessToken) {
    // Read the .env file
    const envFileContent = fs.readFileSync('.env', 'utf8');

    // Replace or add the ACCESS_TOKEN in the .env file
    let updatedEnvFileContent;
    if (envFileContent.includes('ACCESS_TOKEN')) {
        updatedEnvFileContent = envFileContent.replace(/ACCESS_TOKEN=.*/g, `ACCESS_TOKEN=${accessToken}`);
    } else {
        updatedEnvFileContent = `${envFileContent}\nACCESS_TOKEN=${accessToken}`;
    }

    // Write the updated content back to the .env file
    fs.writeFileSync('.env', updatedEnvFileContent, 'utf8');
    console.log('Access token saved to .env file');
}

// Function to get the stored access token
function getStoredAccessToken() {
    console.log('Reading access token from .env file...');
    const envContent = fs.readFileSync('.env', 'utf8');
    const accessTokenMatch = envContent.match(/ACCESS_TOKEN=(.+)/);
    return accessTokenMatch ? accessTokenMatch[1].trim() : null;
}

// Function to check if the access token is expired
function isTokenExpired(token) {
    if (!token) return true; // No token means expired
    const decodedToken = jwt.decode(token);
    const now = Math.floor(Date.now() / 1000); // Current time in seconds
    return decodedToken && decodedToken.exp < now; // Check expiration
}

// Usage example
async function ensureAccessToken() {
    const token = getStoredAccessToken();
    if (isTokenExpired(token)) {
        console.log('Access token is expired or not found. Fetching a new one...');
        await getAccessToken(); // Fetch a new token
    } else {
        console.log('Access token is valid.');
    }
}

module.exports = { getAccessToken, saveAccessTokenToEnv, getStoredAccessToken, ensureAccessToken };
