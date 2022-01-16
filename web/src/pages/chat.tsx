import Link from "next/link";
import React, { useContext, useEffect, useState } from "react";
import ChatMessageBox from "../components/ChatMessageBox";
import { SocketContext } from "../context/context";
import { ChatMessage } from "../models/chatmessage";
import { GetChatHistoriesSuccessMessage } from "../models/message";

function Home() {
	const { socket, authToken, message, user, chatroom } = useContext(SocketContext);
	const [chatMessages, setChatMessages] = useState<ChatMessage[]>([]);

	useEffect(() => {
		if (message instanceof GetChatHistoriesSuccessMessage) {
			setChatMessages(message.messages);
		}
	}, [message]);

	useEffect(() => {
		if (authToken) {
			const body: string = JSON.stringify({ id: chatroom?.id, limit: "1" });
			socket?.send("/chat/histories", body, "POST", authToken);
		}
	}, []);

	return (
		<div className='min-h-screen p-20 bg-base-200'>
			<div className='flex flex-col items-center h-full'>
				<p className='mb-10 text-3xl font-bold'>Chat Room List</p>
				<div className='flex justify-center'>
					<div className='flex flex-col justify-center'>
						{chatMessages.map((chatMessage, i) => (
							<ChatMessageBox user={user} chatMessage={chatMessage} key={i} />
						))}
					</div>
				</div>
				<div className='flex flex-col items-center'>
					<label htmlFor='username' className='text-xl label'>
						Existing room's id:
					</label>
					<input id='password' className='input input-bordered' type='text' />
				</div>
				<div className='mt-5 mb-5 btn btn-accent btn-wide'>Join</div>
				<Link href='/chatmenu'>
					<div className='btn btn-wide'>BACK</div>
				</Link>
			</div>
		</div>
	);
}

export default Home;
