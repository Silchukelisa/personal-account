$(document).ready(function() {
    let td = document.getElementById('data').getElementsByTagName('td');
    let id = td[0].innerText;
    let href = "requests/" + id;
    check(href)
});
document.addEventListener('click', function(event) {
    event.preventDefault()
    if ($(event.target).hasClass('makeAdmin')) {
        let td = document.getElementById('data').getElementsByTagName('td');
        let id = td[0].innerText;
        href = "requests/mail/" + id;
        makeAdmin(href)
        location.reload();
    }
});

function changeButton(data) {
    var bt = document.getElementById('makeAdmin');
    if (data == true) {
        bt.disabled = true;
    } else {
        bt.disabled = false;
    }
}

function makeAdmin(href) {
    fetch(href, {
        method: "GET",
        headers: {
            "Content-Type": "application/json;charset=utf-8"
        },
    })
}

function check(href) {
    fetch(href).then((resp) => resp.json()).then(function(data) {
        changeButton(data);
    }).catch(function(error) {
        console.log(error);
    });
}