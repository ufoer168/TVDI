document.getElementById('username').addEventListener('blur', function(event){
    let feedbackElement = document.getElementById('feedback')
    let username = event.target.value
    if(username.length < 5){
        feedbackElement.textContent = "使用者的名綱必需大新5個字元"
    }else{
        feedbackElement.textContent =  ""
    }
}, false);