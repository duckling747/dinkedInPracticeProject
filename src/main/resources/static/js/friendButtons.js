
const url = contextRoot + "accounts/friendrequests";

const cookieVal = document.cookie
.split('; ')
.find(r => r.startsWith('XSRF-TOKEN'))
.split('=')[1];

function sendJson(successMessage, failMessage, operation, data) {
    console.log("data", data);
    if (!cookieVal) {
        alert("Session cookie not found!");
        return;
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
    const r = confirm("Stop this friendship?");
    if (!r) return;
    sendJson("Friendship stopped!", "Fail", "DELETE", { to, from });
};

function cancelFriendInvite(from, to) {
    const r = confirm("Cancel this friend invite?");
    if (!r) return;
    sendJson("Friend invite canceled!", "Fail", "DELETE", { to, from });
};

function sendFriendInvite(from, to) {
    const r = confirm("Send friend invite?");
    if (!r) return;
    sendJson(
        "Friend invite sent!",
        "Something went wrong with sending friendrequest :(",
        "POST",
        { to, from });
};

function test() {
    console.log("test");
}