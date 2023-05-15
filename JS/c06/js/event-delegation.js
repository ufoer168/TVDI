document.getElementById('shoppingList').addEventListener('click', (event) => {
    event.preventDefault();
    event.target.parentNode.remove();
});