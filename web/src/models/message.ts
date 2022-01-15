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
		const body = JSON.parse(rawBody);
		const status = parseInt(lines[0].split(" ")[1]);
		const headers = lines
			.slice(1, bodyIndex - 1)
			.map((line) => ({ key: line.split(": ", 2)[0], value: line.split(": ", 2)[1] }));
		const path = headers.find((e) => e.key === "Path")?.value;
		if (!path) return new ErrorMessage();

		if (status === 200) {
			// Success
			if (path === "/login") {
				return new LoginSuccessMessage(headers, body);
			}
			if (path === "/register") {
			}
			if (path === "/friend") {
			}
		} else {
			// Fail
		}
		return new ErrorMessage();
	}
}

export class LoginSuccessMessage extends Message {
	constructor(header: Header[], body: any) {
		super(header, body);
		this.authToken = body.authToken;
	}

	public authToken: string;
}

export class LoginFailedMessage extends Message {
	constructor(header: Header[], body: any) {
		super(header, body);
	}
}

export class ListFriendMessage extends Message {
	constructor(header: Header[], body: any) {
		super(header, body);
	}
}

export class ErrorMessage extends Message {
	constructor() {
		super([], {});
	}
}
