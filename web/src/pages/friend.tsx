import Link from "next/link";
import React, { useContext, useEffect, useState } from "react";
import { SocketContext } from "../context/context";
import {
	AddFriendFailedMessage,
	AddFriendSuccessMessage,
	DeleteFriendFailedMessage,
	DeleteFriendSuccessMessage,
	ListFriendMessage,
} from "../models/message";
import { Friend } from "../models/user";

function Friend() {
	const { socket, authToken, message, setMessage, user } = useContext(SocketContext);
	// socket?.send(friend);
	let nullfriend: Friend[] = [];
	const [friendList, setfriendList] = useState(nullfriend);
	const [friendUsername, setFriendUsername] = useState("");

	const onFriendUsernameChange = (e: any) => {
		setFriendUsername(e.target.value);
	};

	const ADD = () => {
		const body: string = JSON.stringify({ username: friendUsername });
		if (authToken) {
			socket?.send("/friend/add", body, "POST", authToken);
		}
	};

	const DELETE = () => {
		const body: string = JSON.stringify({ username: friendUsername });
		if (authToken) {
			socket?.send("/friend/delete", body, "POST", authToken);
		}
	};

	useEffect(() => {
		if (message instanceof ListFriendMessage) {
			let friends: Friend[] = message.friendList;
			setfriendList(friends);
			// let nullMessage:Message = new NullMessage([],"");
			// setMessage(nullMessage);
		}
		if (message instanceof AddFriendSuccessMessage) {
			alert("Add Friend Success!");
			if (authToken) {
				socket?.send("/friend/list", "{}", "POST", authToken);
			}
		}
		if (message instanceof AddFriendFailedMessage) {
			alert("Add Friend Failed");
		}
		if (message instanceof DeleteFriendSuccessMessage) {
			alert("Delete Friend Success!");
			if (authToken) {
				socket?.send("/friend/list", "{}", "POST", authToken);
			}
		}
		if (message instanceof DeleteFriendFailedMessage) {
			alert("Delete Friend Failed");
		}
	}, [message]);

	useEffect(() => {
		if (authToken) {
			socket?.send("/friend/list", "{}", "POST", authToken);
		}
	}, []);

	return (
		<div className='min-h-screen p-20 bg-base-200'>
			<div className='flex flex-col items-center h-full'>
				<p className='mb-10 text-3xl font-bold'>Friend List</p>
				<div className='flex justify-center'>
					{friendList.map(({ username }) => (
						<div className='mx-2 bg-white shadow-md w-60 card'>
							<div className='overflow-hidden card-body'>
								<p className='text-xl font-bold'>{username}</p>
							</div>
						</div>
					))}
				</div>
				<div className='mt-10'>
					<label htmlFor='username' className='text-xl label'>
						Username:
					</label>
					<input
						id='addfriend'
						className='input input-bordered'
						type='text'
						onChange={onFriendUsernameChange}
					/>
				</div>
				<div className='mt-5 btn btn-accent btn-wide' onClick={ADD}>
					ADD
				</div>
				<div className='my-5 btn btn-accent btn-wide' onClick={DELETE}>
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
