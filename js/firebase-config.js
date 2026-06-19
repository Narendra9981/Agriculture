// Firebase configuration and initialization
const firebaseConfig = {
  apiKey: "AIzaSyCYwy6hvXARWK3mALHnT_ppBwrzyOfQYaY",
  authDomain: "farmers-acc82.firebaseapp.com",
  databaseURL: "https://farmers-acc82-default-rtdb.firebaseio.com",
  projectId: "farmers-acc82",
  storageBucket: "farmers-acc82.firebasestorage.app",
  messagingSenderId: "971960077402",
  appId: "1:971960077402:web:cbd59187a26f634b569c63"
};

export let auth = null;
export let database = null;
export let authSDK = null;
export let dbSDK = null;

export const firebaseConfigPromise = Promise.all([
    import("https://www.gstatic.com/firebasejs/10.8.0/firebase-app.js"),
    import("https://www.gstatic.com/firebasejs/10.8.0/firebase-auth.js"),
    import("https://www.gstatic.com/firebasejs/10.8.0/firebase-database.js")
]).then(([appMod, authMod, dbMod]) => {
    const app = appMod.initializeApp(firebaseConfig);
    auth = authMod.getAuth(app);
    database = dbMod.getDatabase(app);
    authSDK = authMod;
    dbSDK = dbMod;
    console.log("Firebase initialized successfully");
}).catch(err => {
    console.warn("Firebase failed to initialize (operating in offline/local mock mode):", err);
});
