import React from "react";
import { ChatMessage } from "../models/chatmessage";
import { User } from "../models/user";

interface Props {
	user?: User;
	chatMessage: ChatMessage;
}

function getDisplayContent({ sender, id, type, content, filename }: ChatMessage) {
	if (type === "text") {
		return content;
	} else {
		return `[${type}]${filename} (id=${id})`;
	}
}

function ChatMessageBox({ user, chatMessage }: Props) {
	const { sender } = chatMessage;
	const displayContent = getDisplayContent(chatMessage);

	return (
		<div className='py-2'>
			<p className='font-semibold pb-0.5'>{sender}:</p>
			<div className='bg-white shadow card card-compact'>
				<div className='mx-4 my-2 overflow-hidden'>{displayContent}</div>
			</div>
		</div>
	);
}

export default ChatMessageBox;
