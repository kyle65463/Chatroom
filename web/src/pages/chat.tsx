import { encode } from "base64-arraybuffer";
import imageType from "image-type";
import { useRouter } from "next/router";
import React, { useContext, useEffect, useRef, useState } from "react";
import ChatMessageBox from "../components/ChatMessageBox";
import { SocketContext } from "../context/context";
import { ChatMessage } from "../models/chatmessage";
import { GetChatHistoriesSuccessMessage } from "../models/message";

function Home() {
	const router = useRouter();
	const { socket, authToken, message, user, chatroom, setChatRoom } = useContext(SocketContext);
	const [chatMessages, setChatMessages] = useState<ChatMessage[]>([]);
	const [textMessage, setTextMessage] = useState("");
	const textInputRef = useRef<HTMLInputElement>(null);
	const fileInputRef = useRef<HTMLInputElement>(null);

	useEffect(() => {
		if (message instanceof GetChatHistoriesSuccessMessage) {
			setChatMessages(message.messages);
		}
	}, [message]);

	useEffect(() => {
		if (authToken) {
			const body = JSON.stringify({ id: chatroom?.id, limit: "1" });
			socket?.send("/chat/histories", body, "POST", authToken);
		}
	}, []);

	const onMessageChange = (e: any) => {
		setTextMessage(e.target.value);
	};

	const onSendTextMessage = () => {
		// Send a text message
		if (authToken && textMessage.trim().length > 0) {
			const body = JSON.stringify({ id: chatroom?.id, type: "text", content: textMessage.trim() });
			socket?.send("/chat/send", body, "POST", authToken);
			setTextMessage("");
			if (textInputRef.current) {
				textInputRef.current.value = "";
			}
		}
	};

	const onSendFileMessage = () => {
		// Send a file message
		if (authToken && fileInputRef.current) {
			fileInputRef.current.click();
			// const body = JSON.stringify({ id: chatroom?.id, type: "text", content: textMessage });
			// socket?.send("/chat/send", body, "POST", authToken);
		}
	};

	const onUploadFileChange = (event: any) => {
		const file: File = event.target.files[0];
		if (file && fileInputRef.current && authToken) {
			const reader = new FileReader();
			reader.readAsArrayBuffer(file);
			reader.onloadend = (event: any) => {
				const result: ArrayBuffer = event.target.result;
				const isImage = imageType(new Uint8Array(result));
				const base64 = encode(result);
				const body = {
					id: chatroom?.id,
					type: isImage ? "image" : "file",
					filename: file.name,
					content: base64,
				};
				alert("Uploading file...");
				socket?.send("/chat/send", JSON.stringify(body), "POST", authToken);
			};
		}
	};

	const back = () => {
		setChatRoom(undefined);
		router.back();
	};

	const onRefresh = () => {
		if (authToken) {
			const body = JSON.stringify({ id: chatroom?.id, limit: "1" });
			socket?.send("/chat/histories", body, "POST", authToken);
		}
	};

	return (
		<div className='h-screen bg-base-200'>
			<div className='flex flex-row justify-between h-screen'>
				<div className='flex py-14 flex-[3.7] pl-60 flex-col pr-10'>
					{/* Messages */}
					<div className='flex flex-row justify-between pb-5'>
						<p className='text-3xl font-bold'>{chatroom?.name}</p>
						<div className='text-gray-600 btn btn-ghost btn-sm' onClick={back}>
							BACK
						</div>
					</div>

					<div className='flex justify-center overflow-y-scroll h-[84%]'>
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
					<div className='mt-5 flex flex-row items-center w-full bottom-[10%]'>
						<input
							id='message'
							className='mr-3 flex-[6] input input-bordered'
							type='text'
							onChange={onMessageChange}
							ref={textInputRef}
						/>
						<div className='flex flex-row items-center flex-1'>
							<div className='mr-3 btn btn-accent' onClick={onSendTextMessage}>
								SEND
							</div>
							<div className='btn' onClick={onSendFileMessage}>
								+
							</div>
							<div className='ml-2 btn' onClick={onRefresh}>
								R
							</div>
						</div>
						<input
							type='file'
							id='file'
							ref={fileInputRef}
							className='hidden'
							onChange={onUploadFileChange}
						/>
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
