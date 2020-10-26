import React, { useEffect, useState } from "react";
import { deleteRejectFriendship, getPendingFriendRequests, putAcceptFriendship } from "../services/user";
import "./PendingFriendRequests.css";




const PendingFriendRequests = (props) => {

    const { id, username, current } = props;

    const [pendingList, setPendingList] = useState([]);

    useEffect(() => {
        getPendingFriendRequests(id)
            .then(bod => {
                console.log("pending reqs:", bod);
                setPendingList(bod);
            });
    }, []);

    const acceptFriend = (friend) => {
        const r = confirm(`Are you sure you want to accept the friendship of ${friend}?`)
        if (!r) return;
        putAcceptFriendship(current, friend)
            .then(bod => {
                console.log(bod);
            });
    };
    
    const rejectFriend = (friend) => {
        const r = confirm(`Are you sure you want to reject the friendship of ${friend}?`)
        if (!r) return;
        deleteRejectFriendship(current, friend)
            .then(bod => {
                console.log(bod);
            });
    };
    
    

    console.log("username", username);
    console.log("current", current);
    if (username !== current) return null;

    if (!pendingList || pendingList.length < 1) return null;

    return (
        <aside>
            <h3>
                !You have pending friend requests!
            </h3>
            <table>
                <thead>
                    <tr>
                        <th>username</th>
                        <th>first name</th>
                        <th>last name</th>
                    </tr>
                </thead>
                <tbody>
                {
                    pendingList.map((p, i) => 
                    <tr key={i}>
                        <td>{p.issuer.username}</td>
                        <td>{p.issuer.firstName}</td>
                        <td>{p.issuer.lastName}</td>
                        <td><button onClick={() => acceptFriend(p.issuer.username)}
                            type="button"
                        >
                            Accept
                        </button></td>
                        <td><button onClick={() => rejectFriend(p.issuer.username)}
                            type="button"
                        >
                            Reject
                        </button></td>
                    </tr>
                    )
                }
                </tbody>
            </table>
            
        </aside>
    );
};

export default PendingFriendRequests;
