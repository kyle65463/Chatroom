import React from "react";
import { ChatMessage } from "../models/chatmessage";
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
	const isSender = sender == user?.username;

	return (
		<div className={`flex ml-20`}>
			<div className='py-2'>
				<p className={`font-semibold pb-0.5`}>{sender}:</p>
				<div
					className={`bg-white shadow card card-compact ${canDownload ? "hover:cursor-pointer" : ""}`}
					onClick={canDownload ? () => onDownloadFile(chatMessage) : () => {}}
				>
					<div className={`mx-4 my-2 overflow-hidden ${canDownload ? "font-bold text-primary" : ""}`}>
						{displayContent}
					</div>
				</div>
			</div>
		</div>
	);
}

export default ChatMessageBox;
