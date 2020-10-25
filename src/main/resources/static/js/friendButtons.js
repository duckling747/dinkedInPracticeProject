
const url = contextRoot + "accounts/friendrequests";

const cookieVal = document.cookie
.split('; ')
.find(r => r.startsWith('XSRF-TOKEN'))
.split('=')[1];

function sendJson(successMessage, failMessage, operation, data) {
    console.log("data", data);
    if (!cookieVal) {
        alert("Session cookie not found!");
    }
    fetch(url, {
        method: operation,
        body: JSON.stringify(data),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'X-XSRF-TOKEN': cookieVal
        }
    })
    .then(response => {
        if (response.ok) {
            alert(successMessage);
            location.reload();
        } else {
            alert(failMessage);
        }
    });
};

function stopFriendship(from, to) {
    sendJson("Friendship stopped!", "Fail", "DELETE", { to, from });
};

function cancelFriendInvite(from, to) {
    sendJson("Friendrequest canceled!", "Fail", "DELETE", { to, from });
};

function sendFriendInvite(from, to) {
    sendJson(
        "Friendrequest sent!",
        "Something went wrong with sending friendrequest :(",
        "POST",
        { to, from });
};

function test() {
    console.log("test");
}