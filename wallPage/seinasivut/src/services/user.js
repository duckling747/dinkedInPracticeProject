export const getUserWithId = async (id) => {
    const res = await fetch(`http://localhost:8080/accounts/${id}`);
    return res.json();
};

export const getPendingFriendRequests = async (userId) => {
    const res = await fetch(`http://localhost:8080/accounts/${userId}/friendrequests`);
    return res.json();
};