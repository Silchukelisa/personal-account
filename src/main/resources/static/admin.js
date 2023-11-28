$(document).ready(function() {
    requestRestartAllUser();
    restartAllUser();
    $('.buttonAdd').on('click', function(event) {
        let user = {
            name: $("#name").val(),
            lastName: $("#lastName").val(),
            email: $("#email").val(),
            password: $("#password").val(),
            userName: $("#userName").val(),
            roles: $("#select_role").val()
        }
        href = getHref($("#select_role"));
        fetch(href, {
            method: "POST",
            headers: {
                "Content-Type": "application/json;charset=utf-8"
            },
            body: JSON.stringify(user)
        }).then(() => openTabById('users')).then(() => restartAllUser());
        $('input').val('');
    });
});

function getHref(address) {
    if (address.val() == 2) {
        return "/actions/ROLE_USER";
    } else {
        return "/actions/ROLE_ADMIN,ROLE_USER";
    }
}

function requestRestartAllUser() {
    let UserTableBody = $("#request_body")
    UserTableBody.children().remove();
    fetch("requests/").then((response) => {
        response.json().then(data => data.forEach(function(item, i, data) {
            let TableRow = requestCreateTableRow(item);
            UserTableBody.append(TableRow);
        }));
    }).catch(error => {
        console.log(error);
    });
}

function requestCreateTableRow(u) {
    let userRole = "";
    let arr = [];
    for (let i = 0; i < u.roles.length; i++) {
        userRole += " " + u.roles[i].name;
        arr.push(u.roles[i].name);
    }
    return `<tr id="request_body_row">
            <td>${u.id}</td>
            <td>${u.name}</td>
            <td>${u.lastName}</td>
            <td>${u.email}</td>
            <td>${u.password}</td>
            <td>${u.username}</td>
            <td>${userRole}</td>
            <td>
            <a  href="/requests/${u.id}" roles = "${arr}" class="btn btn-success acceptBtn" id="acceptBtn">Accept</a>
            <a  href="/requests/${u.id}" class="btn btn-danger rejectBtn">Reject</a>
            </td>
        </tr>`;
}

function openTabById(tab) {
    $('.nav-item a[href="#' + tab + '"]').tab('show');
}

function restartAllUser() {
    let UserTableBody = $("#user_table_body")
    UserTableBody.children().remove();
    fetch("actions/").then((response) => {
        response.json().then(data => data.forEach(function(item, i, data) {
            let TableRow = createTableRow(item);
            UserTableBody.append(TableRow);
        }));
    }).catch(error => {
        console.log(error);
    });
}

function createTableRow(u) {
    let userRole = "";
    let arr = [];
    for (let i = 0; i < u.roles.length; i++) {
        userRole += " " + u.roles[i].name;
        arr.push(u.roles[i].name);
    }
    return `<tr id="user_table_row">
            <td>${u.id}</td>
            <td>${u.name}</td>
            <td>${u.lastName}</td>
            <td>${u.email}</td>
            <td>${u.password}</td>
            <td>${u.username}</td>
            <td>${userRole}</td>
            <td>
            <a  href="/actions/${u.id}" roles = "${arr}" data-bs-toggle="modal" data-bs-target="#exampleModal" class="btn btn-primary eBtn">Edit</a>
            <a  href="/actions/${u.id}" class="btn btn-danger delBtn">Delete</a>
            </td>
        </tr>`;
}
document.addEventListener('click', function(event) {
    event.preventDefault()
    if ($(event.target).hasClass('acceptBtn')) {
        let href = $(event.target).attr("href");
        updateRequest(href)
    }
    if ($(event.target).hasClass('rejectBtn')) {
        let href = $(event.target).attr("href");
        deleteRequest(href)
    }
    if ($(event.target).hasClass('delBtn')) {
        let href = $(event.target).attr("href");
        delModalButton(href)
    }
    if ($(event.target).hasClass('eBtn')) {
        let href = $(event.target).attr("href");
        $(".editUser #exampleModal").modal();
        $.get(href, function(user) {
            $(".editUser #id").val(user.id);
            $(".editUser #nameEd").val(user.name);
            $(".editUser #lastNameEd").val(user.lastName);
            $(".editUser #emailEd").val(user.email);
            $(".editUser #passwordEd").val(user.name);
            $(".editUser #userNameEd").val(user.userName);
            if (!($(event.target).attr("roles").includes("ROLE_ADMIN"))) {
                $(".editUser #selectRoleEd").val("2");
            }
        });
    }
    if ($(event.target).hasClass('editButton')) {
        let user = {
            id: $('#id').val(),
            name: $('#nameEd').val(),
            lastName: $('#lastNameEd').val(),
            email: $('#emailEd').val(),
            password: $('#passwordEd').val(),
            userName: $('#userNameEd').val()
        }
        href = getRoleEdit($("#selectRoleEd"));
        editModalButton(user, href)
    }
});

function getRoleEdit(address) {
    if (address.val() == 2) {
        return "/actions/ROLE_USER";
    } else {
        return "/actions/ROLE_ADMIN,ROLE_USER";
    }
}

function editModalButton(user, href) {
    fetch(href, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json;charset=utf-8"
        },
        body: JSON.stringify(user)
    }).then(function(response) {
        $('input').val('');
        $('.editUser #exampleModal').modal('hide');
        restartAllUser();
    })
}

function delModalButton(href) {
    fetch(href, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json;charset=utf-8"
        }
    }).then(() => restartAllUser());
}

function updateRequest(href) {
    fetch(href, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json;charset=utf-8"
        },
    }).then(function(response) {
        $('input').val('');
        requestRestartAllUser();
        restartAllUser()
    })
}

function deleteRequest(href) {
    fetch(href, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json;charset=utf-8"
        },
    }).then(function(response) {
        $('input').val('');
        requestRestartAllUser();
        restartAllUser()
    })
}

function openRole(evt, role) {
    var i, tabcontent, tablinks;
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }
    document.getElementById(role).style.display = "block";
    evt.currentTarget.className += " active";
}
document.getElementById("defaultOpen").click();