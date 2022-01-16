import Link from "next/link";
import React, { useContext, useEffect, useState } from "react";
import { SocketContext } from "../context/context";
import { AddFriendFailedMessage, AddFriendSuccessMessage, DeleteFriendFailedMessage, DeleteFriendSuccessMessage, ListFriendMessage, Message, NullMessage } from "../models/message";
import {Friend} from "../models/user";

function Friend() {
	const { socket, authToken ,message, setMessage, user } = useContext(SocketContext);
    // socket?.send(friend);

    const [friendList, setfriendList] = useState<Friend[]>([]);
    const [addfriend, setAddFriend] = useState("");
	const [deletefriend, setDeleteFriend] = useState("");

    const onAddFriendChange = (e: any) => {
		setAddFriend(e.target.value);
	};
    
    const onDeleteriendChange = (e: any) => {
		setDeleteFriend(e.target.value);
	};
    
    const ADD = () =>{
        const body: string = JSON.stringify({username:addfriend});
        if(authToken){
            socket?.send("/friend/add",body,"POST",authToken);
        }
    }

    const DELETE = () =>{
        const body: string = JSON.stringify({username:deletefriend});
        if(authToken){
            socket?.send("/friend/delete",body,"POST",authToken);
        }
    }
    


	useEffect(() => {
        if(message instanceof ListFriendMessage){
            let friends:Friend[] = message.friendList;
            setfriendList(friends);
            // let nullMessage:Message = new NullMessage([],"");
            // setMessage(nullMessage);
        }
        if(message instanceof AddFriendSuccessMessage){
            alert("Add Friend Success!");
            if(authToken){
                socket?.send("/friend/list","{}","POST",authToken);
            }
        }
        if(message instanceof AddFriendFailedMessage){
            alert("Add Friend Failed");
        }
        if(message instanceof DeleteFriendSuccessMessage){
            alert("Delete Friend Success!");
            if(authToken){
                socket?.send("/friend/list","{}","POST",authToken);
            }
        }
        if(message instanceof DeleteFriendFailedMessage){
            alert("Delete Friend Failed");
        }
    }, [message]);

    useEffect(()=>{
        if(authToken){
            socket?.send("/friend/list","{}","POST",authToken);
        }
    },[])


	return (
		<div className='h-screen'>
			<div className='flex flex-col items-center h-full'>
				<p className="text-3xl m-10">Friend List</p>
                <div className='flex justify-center'>
                    {friendList.map((friend, i) => (
                        <div className='bg-gray-100  p-4 mx-3'>
                            {/* <p className='flex flex-col items-start'>friend {i}</p> */}
                            <div className='flex flex-row items-start'>
                                <p className='pr-2 py-1'>UserName: {friend.username}</p>
                                {/* <p className='py-1'>DisplayName:{friend.displayName}</p> */}
                            </div>
                        </div>
                    ))}
			    </div>
                <div>
                    <div className="flex flex-col items-center m-5">
                        <label htmlFor='username' className='label text-xl'>
                            ADD:
                        </label>
                        <input id='addfriend' className='input input-bordered' type='text' onChange={onAddFriendChange}/>
                    </div>
                    <div className='m-10 btn btn-accent btn-wide' onClick={ADD}>
                        ADD
                    </div>
                    <div className="flex flex-col items-center">
                        <label htmlFor='username' className='label text-xl'>
                            Delete:
                        </label>
                        <input id='password' className='input input-bordered' type='text' onChange={onDeleteriendChange}/>
                    </div>
				</div>
				<div className='m-10 btn btn-accent btn-wide' onClick={DELETE}>
					Delete
				</div>
                <Link href='/home'>
					<div className='btn btn-wide'>BACK</div>
				</Link>
			</div>

		</div>
	);
}

export default Friend;
