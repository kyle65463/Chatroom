export interface ChatMessage {
	id: string;
	type: string;
	sender: string;
	chatroomId: string;
	content?: string;
	filename?: string;
}
