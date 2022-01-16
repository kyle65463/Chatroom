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
		<div className='h-screen bg-base-200'>
			<div className='flex flex-row justify-between h-screen'>
				<div className='flex py-14 flex-[3.7] pl-60 flex-col pr-10'>
					{/* Messages */}
					<p className='text-3xl font-bold'>{chatroom?.name}</p>
					<div className='flex justify-center overflow-y-scroll h-[85%]'>
						<div className='flex flex-col justify-center'>
							{chatMessages.map((chatMessage, i) => (
								<ChatMessageBox user={user} chatMessage={chatMessage} key={i} />
							))}
							{chatMessages.map((chatMessage, i) => (
								<ChatMessageBox user={user} chatMessage={chatMessage} key={i} />
							))}
							{chatMessages.map((chatMessage, i) => (
								<ChatMessageBox user={user} chatMessage={chatMessage} key={i} />
							))}
						</div>
					</div>

					{/* Input field */}
					<div className='flex flex-row items-center w-full bottom-[10%]'>
						<input id='message' className='mr-3 flex-[7] input input-bordered' type='text' />
						<div className='flex-1 mt-5 mb-5 btn btn-accent'>SEND</div>
					</div>
				</div>
				<div className='flex-1 bg-gray-200'>
					{/* Users */}
					<p className='px-6 mb-4 text-xl font-bold pt-7'>Users</p>
					{chatroom?.usernames.map((name, i) => (
						<div className='mx-6 my-3 bg-white card' key={i}>
							<p className='px-4 py-2 text-lg card-body'>{name}</p>
						</div>
					))}
				</div>
			</div>
		</div>
	);
}

export default Home;
