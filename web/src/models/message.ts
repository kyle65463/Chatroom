export abstract class Message {
	public static loginParse(data: string): Message {
		let index: number = data.indexOf("OK");
		if (index == -1) {
			console.log("login failed");
			return new LoginFailedMessage();
		}
		let authIndex: number = data.indexOf("authToken");
		let authToken: string = "";
		for (let i: number = authIndex + 12; i < data.length; i++) {
			if (data[i] == '"') {
				break;
			}
			authToken += data[i];
		}
		console.log(authToken);
		return new LoginSuccessMessage(authToken);
	}

	public static parse(message: MessageEvent<any>): Message {
		const lines = (message.data as string).split("\r\n");
		const bodyIndex = lines.findIndex((e) => e == "") + 1;
		const body = lines[bodyIndex];
		const status = parseInt(lines[0].split(" ")[1]);
		const headers = lines.slice(1, bodyIndex - 1).map((line) => line.split(": ", 2));

		let data: string = message.data;
		let path_Index: number = data.indexOf("Path: /");
		let path: string = "";
		for (let i: number = path_Index + 7; i < data.length; i++) {
			if (data[i] == "\r") {
				break;
			}
			path += data[i];
		}
		if (path == "login") {
			return Message.loginParse(data);
		}
		console.log(path);
		let line: string;
		return new ListFriendMessage(["luca"]);
	}
}

export class LoginSuccessMessage extends Message {
	constructor(authToken: string) {
		super();
		this.authToken = authToken;
	}
	public authToken: string;
}

export class LoginFailedMessage extends Message {}

export class ListFriendMessage extends Message {
	constructor(friends: string[]) {
		super();
		this.friends = friends;
	}

	public friends: string[] = [];
}
