import React, { useContext, useEffect, useState } from "react";
import { SocketContext } from "../context/context";
import { ChatMessage } from "../models/chatmessage";
import { DownloadFileSuccessMessage } from "../models/message";
import { User } from "../models/user";

interface Props {
	user?: User;
	chatMessage: ChatMessage;
	onDownloadFile: (message: ChatMessage) => void;
}

function getDisplayContent({ type, content, filename }: ChatMessage) {
	if (type === "text") {
		return content;
	} else {
		return filename;
	}
}

function ChatMessageBox({ user, chatMessage, onDownloadFile }: Props) {
	const { sender } = chatMessage;
	const displayContent = getDisplayContent(chatMessage);
	const canDownload = chatMessage.type === "file";
	const [imgData, setImgData] = useState<string | undefined>();

	const { message, authToken, chatroom, socket } = useContext(SocketContext);
	const Img = () => <img src={`data:image/jpeg;base64,${imgData}`} />;

	useEffect(() => {
		if (chatMessage.type === "image") {
			if (authToken) {
				const body = {
					id: chatroom?.id,
					type: "image",
					filename: chatMessage.filename,
					messageId: chatMessage.id,
				};
				socket?.send("/chat/download", JSON.stringify(body), "POST", authToken);
			}
		}
	}, []);

	useEffect(() => {
		if (message instanceof DownloadFileSuccessMessage) {
			if (message.type === "image" && message.id === chatMessage.id) {
				setImgData(message.file);
			}
		}
	}, [message]);

	return (
		<div className={`flex ml-20 max-w-[530px]`}>
			<div className='py-2'>
				<p className={`font-semibold pb-0.5`}>{sender}:</p>
				<div
					className={`bg-white shadow card card-compact ${canDownload ? "hover:cursor-pointer" : ""}`}
					onClick={canDownload ? () => onDownloadFile(chatMessage) : () => {}}
				>
					<div className={`mx-4 my-2 overflow-hidden ${canDownload ? "font-bold text-primary" : ""}`}>
						{imgData == undefined ? displayContent : <Img />}
					</div>
				</div>
			</div>
		</div>
	);
}

export default ChatMessageBox;
