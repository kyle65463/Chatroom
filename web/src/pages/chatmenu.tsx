import Link from "next/link";
import React, { useContext, useEffect, useState } from "react";
import { SocketContext } from "../context/context";
import { CreateChatRoomFailedMessage, CreateChatRoomSuccessMessage, JoinChatRoomFailedMessage, JoinChatRoomSuccessMessage, ListChatRoomMessage } from "../models/message";
import { ChatRoom } from "../models/user";

function ChatMenu() {
	const { socket, authToken ,message, user } = useContext(SocketContext);

	let nullchatroomlist:ChatRoom[] = [];
    const [ChatroomList, setchatroomList] = useState(nullchatroomlist);
	const [createChatRoom, setcreateChatRoom] = useState("");
	const [joinChatRoom, setjoinChatRoom] = useState("");
	


	const oncreateChatRoomChange = (e: any) => {
		setcreateChatRoom(e.target.value);
	};

	const onjoinChatRoomChange = (e: any) => {
		setjoinChatRoom(e.target.value);
	};

	const CREATE = () =>{
		const body: string = JSON.stringify({name:createChatRoom});
		if(authToken){
			socket?.send("/chatroom/create",body,"POST",authToken);
		}
	}

	const JOIN = () =>{
		const body: string = JSON.stringify({id:joinChatRoom,username:user?.username});
		if(authToken){
			socket?.send("/chatroom/user/add",body,"POST",authToken);
		}
	}

	useEffect(()=>{
        if(authToken){
            socket?.send("/chatroom/list","{}","POST",authToken);
        }
    },[])


	useEffect(() => {
		if(message instanceof ListChatRoomMessage){
			let chatroomlist:ChatRoom[] = message.chatroomlist;
			setchatroomList(chatroomlist);
		}
		if(message instanceof CreateChatRoomSuccessMessage){
			alert("Create Chat Room Success");
			if(authToken){
				socket?.send("/chatroom/list","{}","POST",authToken);
			}
		}
		if(message instanceof CreateChatRoomFailedMessage){
			alert("Create Chat Room Failed");
		}
		if(message instanceof JoinChatRoomSuccessMessage){
			alert("Join Chat Room Success");
			if(authToken){
				socket?.send("/chatroom/list","{}","POST",authToken);
			}
		}
		if(message instanceof JoinChatRoomFailedMessage){
			alert("Join Chat Room Failed");
		}
	}, [message]);

	return (
		<div className='h-screen'>
			<div className='flex flex-col items-center h-full'>
				<p className="text-3xl m-10">Chat Room List</p>
                <div className='flex justify-center'>
					<div className='flex justify-center'>
						{ChatroomList.map((chatroom, i) => (
							<div className='bg-gray-100  p-4 mx-3'>
								<div className='flex flex-row items-start'>
									<div>
									<p className='flex flex-col items-start'>chatroom {i}</p>
									<p className='pr-2 py-1'>ID: {chatroom.id}</p>
									<p className='pr-2 py-1'>Name: {chatroom.name}</p>
									<p className='pr-2 py-1'>Users:</p>
									{chatroom.usernames.map((us,j)=>(
										<p className='pr-2 py-1'> {us} </p>
									))}
									</div>
								</div>
							</div>
						))}
					</div>
			    </div>
                <div>
				<div className="flex flex-col items-center m-5">
					<label htmlFor='username' className='label text-xl'>
						Create:
					</label>
					<input id='addfriend' className='input input-bordered' type='text' onChange={oncreateChatRoomChange}/>
				</div>
                <div className='m-10 btn btn-accent btn-wide' onClick={CREATE}>
					Create
				</div>
				<div className="flex flex-col items-center">
					<label htmlFor='username' className='label text-xl'>
						Enter:
					</label>
					<input id='password' className='input input-bordered' type='text'/>
				</div>
				</div>
				<div className='m-10 btn btn-accent btn-wide'>
					Enter
				</div>
				<div className="flex flex-col items-center">
					<label htmlFor='username' className='label text-xl'>
						Join:
					</label>
					<input id='password' className='input input-bordered' type='text' onChange={onjoinChatRoomChange}/>
				</div>
				<div className='m-10 btn btn-accent btn-wide' onClick={JOIN}>
					Join
				</div>
                <Link href='/home'>
					<div className='btn btn-wide'>BACK</div>
				</Link>
			</div>

		</div>
	);
}

export default ChatMenu;
