import Link from "next/link";
import { useRouter } from "next/router";
import React, { useContext, useEffect, useState } from "react";
import { SocketContext } from "../context/context";
import {
	CreateChatRoomFailedMessage,
	CreateChatRoomSuccessMessage,
	JoinChatRoomFailedMessage,
	JoinChatRoomSuccessMessage,
	ListChatRoomMessage,
} from "../models/message";
import { ChatRoom } from "../models/user";

function ChatMenu() {
	const { socket, authToken, message, user, setChatRoom } = useContext(SocketContext);

	const router = useRouter();
	const [chatroomList, setchatroomList] = useState<ChatRoom[]>([]);
	const [createChatRoom, setcreateChatRoom] = useState("");
	const [joinChatRoom, setjoinChatRoom] = useState("");

	const oncreateChatRoomChange = (e: any) => {
		setcreateChatRoom(e.target.value);
	};

	const onjoinChatRoomChange = (e: any) => {
		setjoinChatRoom(e.target.value);
	};

	const CREATE = () => {
		const body: string = JSON.stringify({ name: createChatRoom });
		if (authToken) {
			socket?.send("/chatroom/create", body, "POST", authToken);
		}
	};

	const JOIN = () => {
		const body: string = JSON.stringify({ id: joinChatRoom, username: user?.username });
		if (authToken) {
			socket?.send("/chatroom/user/add", body, "POST", authToken);
		}
	};

	const ENTER = (index: number) => {
		if (index < 0 || index >= chatroomList.length) {
			alert("Chat Room Nonexist");
		} else {
			setChatRoom(chatroomList[index]);
			router.push("/chat");
		}
	};

	useEffect(() => {
		if (authToken) {
			socket?.send("/chatroom/list", "{}", "POST", authToken);
		}
	}, []);

	useEffect(() => {
		if (message instanceof ListChatRoomMessage) {
			let chatroomlist: ChatRoom[] = message.chatroomlist;
			setchatroomList(chatroomlist);
		}
		if (message instanceof CreateChatRoomSuccessMessage) {
			alert("Create Chat Room Success");
			if (authToken) {
				socket?.send("/chatroom/list", "{}", "POST", authToken);
			}
		}
		if (message instanceof CreateChatRoomFailedMessage) {
			alert("Create Chat Room Failed");
		}
		if (message instanceof JoinChatRoomSuccessMessage) {
			alert("Join Chat Room Success");
			if (authToken) {
				socket?.send("/chatroom/list", "{}", "POST", authToken);
			}
		}
		if (message instanceof JoinChatRoomFailedMessage) {
			alert("Join Chat Room Failed");
		}
	}, [message]);

	return (
		<div className='min-h-screen p-20 bg-base-200'>
			<div className='flex flex-col items-center h-full'>
				<p className='mb-10 text-3xl font-bold'>Chat Room List</p>
				<div className='flex justify-center'>
					<div className='flex justify-center'>
						{chatroomList.map(({ name, id, usernames }, i) => (
							<div
								className='mx-2 bg-white shadow-md w-60 card hover:cursor-pointer'
								onClick={() => ENTER(i)}
							>
								<div className='overflow-hidden card-body'>
									<p className='card-title'>
										{name} (id:{id})
									</p>

									<p className='py-1 font-bold'>{usernames.length} users:</p>
									{usernames.join(", ")}
								</div>
							</div>
						))}
					</div>
				</div>
				<div className='flex flex-col items-center mt-10'>
					<label htmlFor='username' className='text-xl label'>
						New room's name:
					</label>
					<input
						id='password'
						className='input input-bordered'
						type='text'
						onChange={oncreateChatRoomChange}
					/>
				</div>
				<div className='my-5 btn btn-accent btn-wide' onClick={CREATE}>
					Create
				</div>
				<div className='flex flex-col items-center'>
					<label htmlFor='username' className='text-xl label'>
						Existing room's id:
					</label>
					<input id='password' className='input input-bordered' type='text' onChange={onjoinChatRoomChange} />
				</div>
				<div className='mt-5 mb-5 btn btn-accent btn-wide' onClick={JOIN}>
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
