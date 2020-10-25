import React, { useEffect, useState } from "react";
import { getPendingFriendRequests } from "../services/user";

const PendingFriendRequests = (props) => {

    const [pendingList, setPendingList] = useState([]);

    useEffect(() => {
        getPendingFriendRequests(props.id)
            .then(bod => {
                console.log(bod);
                setPendingList(bod);
            });
        //setPendingList([{ issuer: "test" }])
    }, []);

    if (!pendingList || pendingList.length < 1) return null;

    return (
        <div>
            {
                pendingList.map((p, i) => 
                <div>
                    {p.issuer}
                </div>
                )
            }
        </div>
    );
};

export default PendingFriendRequests;
