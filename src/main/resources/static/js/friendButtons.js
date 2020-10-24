
const url = contextRoot + "accounts/friendrequests";

function sendJson(successMessage, failMessage, operation, data) {
    fetch(url, {
        method: operation,
        body: JSON.stringify(data),
        headers: {
            "Content-Type": "application/json"
        }
    })
    .then(response => {
        if (response.ok) {
            alert(successMessage);
        } else {
            alert(failMessage);
        }
    });
};

function stopFriendship(from, to) {
    sendJson("Friendship stopped!", "Fail", "DELETE", { to, from })
};

function cancelFriendInvite(from, to) {
    sendJson("Friendrequest canceled!", "Fail", "DELETE", { to, from })
};

function sendFriendInvite(from, to) {
    sendJson(
        "Friendrequest sent!",
        "Something went wrong with sending friendrequest :(",
        "POST",
        { to, from })
};
