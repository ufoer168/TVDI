import { initializeApp } from 'https://www.gstatic.com/firebasejs/9.22.1/firebase-app.js'

import { getFirestore, collection, getDocs } from 'https://www.gstatic.com/firebasejs/9.22.1/firebase-firestore.js'

const firebaseConfig = {
    apiKey: "AIzaSyCDUR20Nn6nqWP1QKSvIGiVQXt6UVLupOU",
    authDomain: "stock-3a12c.firebaseapp.com",
    projectId: "stock-3a12c",
    storageBucket: "stock-3a12c.appspot.com",
    messagingSenderId: "170890949192",
    appId: "1:170890949192:web:edb08d5c43ed3c3235de7d"
};
const app = initializeApp(firebaseConfig);
const db = getFirestore(app);

const querySnapshot = await getDocs(collection(db, "products"));
let contents = ""
querySnapshot.forEach(doc => {
    console.log(doc.id)
    let documentData = doc.data()
    console.log(documentData['productName'])
    contents += `<tr><th scope="row">${doc.id}</th><td>${documentData['productName']}</td><td>${documentData['code']}</td></tr>`
});

let tbodyElement = document.querySelector('#tbody')
tbodyElement.innerHTML = contents