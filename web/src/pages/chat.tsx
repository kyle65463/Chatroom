import { decode, encode } from "base64-arraybuffer";
import imageType from "image-type";
import { useRouter } from "next/router";
import React, { useContext, useEffect, useRef, useState } from "react";
import ChatMessageBox from "../components/ChatMessageBox";
import { SocketContext } from "../context/context";
import { ChatMessage } from "../models/chatmessage";
import {
	DownloadFileSuccessMessage,
	GetChatHistoriesSuccessMessage,
	SendMessageSuccessMessage,
} from "../models/message";

export function createAndDownloadFile(body: ArrayBuffer, filename: string) {
	const blob = new Blob([body]);
	const link = document.createElement("a");
	if (link.download !== undefined) {
		const url = URL.createObjectURL(blob);
		link.setAttribute("href", url);
		link.setAttribute("download", filename);
		link.style.visibility = "hidden";
		document.body.appendChild(link);
		link.click();
		document.body.removeChild(link);
	}
}

function Home() {
	const router = useRouter();
	const { socket, authToken, message, user, chatroom, setChatRoom } = useContext(SocketContext);
	const [chatMessages, setChatMessages] = useState<ChatMessage[]>([]);
	const [textMessage, setTextMessage] = useState("");
	const textInputRef = useRef<HTMLInputElement>(null);
	const fileInputRef = useRef<HTMLInputElement>(null);
	const messagesEndRef = useRef(null);

	const scrollToBottom = () => {
		//@ts-ignore
		messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
	};

	useEffect(() => {
		scrollToBottom();
	}, [chatMessages]);

	useEffect(() => {
		if (message instanceof GetChatHistoriesSuccessMessage) {
			setChatMessages(message.messages);
		}
		if (message instanceof DownloadFileSuccessMessage) {
			if (message.type === "file") {
				createAndDownloadFile(decode(message.file), message.filename);
			}
		}
		if (message instanceof SendMessageSuccessMessage) {
			onRefresh();
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
		}
	};

	const onUploadFileChange = (event: any) => {
		const file: File = event.target.files[0];
		if (file && fileInputRef.current && authToken) {
			const reader = new FileReader();
			reader.readAsArrayBuffer(file);
			reader.onload = (event: any) => {
				const result: ArrayBuffer = event.target.result;
				const isImage = imageType(new Uint8Array(result));
				const base64 = encode(result);
				const body = {
					id: chatroom?.id,
					type: isImage ? "image" : "file",
					filename: file.name,
					content: base64,
				};
				socket?.send("/chat/send", JSON.stringify(body), "POST", authToken);
			};
		}
	};

	const onDownloadFile = (message: ChatMessage) => {
		if (authToken) {
			const body = {
				id: chatroom?.id,
				type: message.type,
				filename: message.filename,
				messageId: message.id,
			};
			socket?.send("/chat/download", JSON.stringify(body), "POST", authToken);
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

					<div className='overflow-y-scroll h-[84%]'>
						<div className='flex flex-col justify-center'>
							{chatMessages.map((chatMessage, i) => (
								<ChatMessageBox
									user={user}
									chatMessage={chatMessage}
									onDownloadFile={onDownloadFile}
									key={i}
								/>
							))}
						</div>
						<div ref={messagesEndRef} />
					</div>

					{/* Input field */}
					<div className='mt-5 pl-20 flex flex-row items-center w-full bottom-[10%]'>
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
								<svg
									xmlns='http://www.w3.org/2000/svg'
									aria-hidden='true'
									role='img'
									width='0.91em'
									height='1em'
									preserveAspectRatio='xMidYMid meet'
									viewBox='0 0 928 1024'
								>
									<path
										d='M449 899v-1l-92-144q-5-8-14-10.5t-18 2.5l-8 5q-8 4-8.5 14t4.5 18l59 92q-2-1-5-1.5t-5.5-1t-4.5-1.5q-52-13-97.5-40T178 765.5T117 680Q45 541 93 392q11-36 29.5-68.5T165 263t53.5-51t63.5-41q11-6 15-18t-2-23.5t-18-15.5t-23 2q-79 41-136.5 107.5T34 373Q7 457 14.5 543.5T62 708q43 83 116 141.5T341 931q1 1 2 1h2l-81 42q-8 4-10.5 13t2.5 17l3 8q11 18 29 9l149-77l10-5q8-5 10.5-14t-2.5-17zm417-578q-43-83-114.5-141.5T590 98q-9-3-26-6l80-40q9-5 12-14t-1-17l-3-8q-3-5-8.5-8T632 2t-12 2L471 81l-10 5q-9 5-11 14t3 17l5 10h1l92 144q5 8 14 10.5t17-2.5l8-5q8-4 9-14t-4-18l-59-92q22 3 38 8q22 6 43 14t40.5 19t37.5 24t34.5 28t31 32t27 35.5T811 349q72 139 24 288q-23 72-71 129t-115 92q-11 6-15 18t2 24q8 17 27 17q8 0 15-4q78-40 134-106.5T894 656q54-167-23-325z'
										fill='currentColor'
									/>
								</svg>
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
			<script src='https://code.iconify.design/2/2.1.1/iconify.min.js'></script>
		</div>
	);
}

export default Home;
