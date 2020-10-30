
const cookieVal = document.cookie
      .split('; ')
      .find(r => r.startsWith('XSRF-TOKEN'))
      .split('=')[1];

export const postLogout = async () => {
    const res = await fetch("/logout", {
        method: "POST",
        headers: {
            'X-XSRF-TOKEN': cookieVal
        }
    });
    return res;
};

export const getUserWithId = async (id) => {
    const res = await fetch(`/accounts/${id}`);
    return res.json();
};

export const getPendingFriendRequests = async (userId) => {
    const res = await fetch(`/accounts/${userId}/friendrequests`);
    return res.json();
};

export const getCurrentUser = async () => {
    const res = await fetch("/accounts/logged", {
        headers: {
            'X-XSRF-TOKEN': cookieVal
        }
    });
    return res.json();
};

export const putAcceptFriendship = async (from, to) => {
    const res = await fetch("/accounts/friendrequests", {
        method: "PUT",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',    
            'X-XSRF-TOKEN': cookieVal
        },
        body: JSON.stringify({
            from,
            to
        })
    });
    return res.json();
};

export const deleteRejectFriendship = async (from, to) => {
    const res = await fetch("/accounts/friendrequests", {
        method: "DELETE",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',    
            'X-XSRF-TOKEN': cookieVal
        },
        body: JSON.stringify({
            from,
            to
        })
    });
    return res.json();
};

export const getUserAndFriendsPosts = async (id) => {
    const res = await fetch(`/accounts/${id}/posts`);
    return res.json();
};

export const postPost = async (id, post) => {
    const res = await fetch(`/accounts/${id}/posts`, {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',    
            'X-XSRF-TOKEN': cookieVal
        },
        body: JSON.stringify({
            post
        })
    });
    return res;
};

export const likePost = async (userId, postId) => {
    const res = await fetch(`/accounts/${userId}/posts/${postId}/like`, {
        method: "POST",
        headers: {
            'X-XSRF-TOKEN': cookieVal
        }
    });
    return res;
};
