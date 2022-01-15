import { ChatRoom, Friend, User } from "./user";

interface Header {
	key: string;
	value: string;
}

export abstract class Message {
	constructor(header: Header[], body: any) {
		this.header = header;
		this.body = body;
	}

	public header: Header[];
	public body: any;

	public static parse(message: MessageEvent<any>): Message {
		const lines = (message.data as string).split("\r\n");
		const bodyIndex = lines.findIndex((e) => e == "") + 1;
		const rawBody = lines[bodyIndex];
		let body;
		try{
			body = JSON.parse(rawBody);
		}catch(e){
			body = rawBody;
		}
		const status = parseInt(lines[0].split(" ")[1]);
		const headers = lines
			.slice(1, bodyIndex - 1)
			.map((line) => ({ key: line.split(": ", 2)[0], value: line.split(": ", 2)[1] }));
		const path = headers.find((e) => e.key === "Path")?.value;
		if (!path) return new ErrorMessage();
		// console.log(path);
		if (status === 200) {
			// Success
			if (path === "/login") {
				return new LoginSuccessMessage(headers, body);
			}
			if (path === "/register") {
				return new RegisterSuccessMessage(headers, body);
			}
			if (path === "/friend/list") {
				return new ListFriendMessage(headers, body);
			}
			if (path === "/friend/add") {
				return new AddFriendSuccessMessage(headers, body);
			}
			if (path === "/friend/delete"){
				return new DeleteFriendSuccessMessage(headers, body);
			}
			if (path === "/chatroom/list"){
				return new ListChatRoomMessage(headers, body);
			}
			if (path === "/chatroom/create"){
				return new CreateChatRoomSuccessMessage(headers,body);
			}
			if (path === "/chatroom/user/add"){
				return new JoinChatRoomSuccessMessage(headers,body);
			}
		} else {
			// Fail
			if (path === "/login") {
				return new LoginFailedMessage(headers, body);
			}
			if (path === "/register") {
				return new RegisterFailedMessage(headers, body);
			}
			if (path == "/friend/add") {
				return new AddFriendFailedMessage(headers, body);
			}
			if (path === "/friend/delete"){
				return new DeleteFriendFailedMessage(headers, body);
			}
			if (path === "/chatroom/create"){
				return new CreateChatRoomFailedMessage(headers,body);
			}
			if (path === "/chatroom/user/add"){
				return new JoinChatRoomFailedMessage(headers,body);
			}
		}
		return new ErrorMessage();
	}
}

export class NullMessage extends Message{
	constructor(header: Header[], body: any) {
		super(header, body);
	}
}

export class LoginSuccessMessage extends Message {
	constructor(header: Header[], body: any) {
		super(header, body);
		this.authToken = body.authToken;
		this.user = body.user;
	}

	public authToken: string;
	public user: User;
}

export class LoginFailedMessage extends Message {
	constructor(header: Header[], body: any) {
		super(header, body);
	}
}

export class RegisterSuccessMessage extends Message {
	constructor(header: Header[], body: any) {
		super(header, body);
	}
}

export class RegisterFailedMessage extends Message {
	constructor(header: Header[], body: any) {
		super(header, body);
	}
}




export class ListFriendMessage extends Message {
	constructor(header: Header[], body: any) {
		super(header, body);
		this.friendList = body.friends;
	}
	public friendList:Friend[];
}

export class AddFriendSuccessMessage extends Message {
	constructor(header: Header[], body: any) {
		super(header, body);
	}
}

export class AddFriendFailedMessage extends Message {
	constructor(header: Header[], body: any) {
		super(header, body);
	}
}

export class DeleteFriendSuccessMessage extends Message {
	constructor(header: Header[], body: any) {
		super(header, body);
	}
}

export class DeleteFriendFailedMessage extends Message {
	constructor(header: Header[], body: any) {
		super(header, body);
	}
}

export class ListChatRoomMessage extends Message {
	constructor(header: Header[], body: any) {
		super(header, body);
		this.chatroomlist = body.chatrooms;
	}
	public chatroomlist:ChatRoom[];
}

export class CreateChatRoomSuccessMessage extends Message{
	constructor(header: Header[], body: any) {
		super(header, body);
	}
}

export class CreateChatRoomFailedMessage extends Message{
	constructor(header: Header[], body: any) {
		super(header, body);
	}
}

export class JoinChatRoomSuccessMessage extends Message{
	constructor(header: Header[], body: any) {
		super(header, body);
	}
}


export class JoinChatRoomFailedMessage extends Message{
	constructor(header: Header[], body: any) {
		super(header, body);
	}
}




export class ErrorMessage extends Message {
	constructor() {
		super([], {});
	}
}
