export abstract class Message {
	public static parse(message: MessageEvent<any>): Message {
		// Classify what message it is
		// Parse
		return new ListFriendMessage(["luca"]);
		return new LoginSuccessMessage();
	}
}

export class LoginSuccessMessage extends Message {}

export class ListFriendMessage extends Message {
	constructor(friends: string[]) {
		super();
		this.friends = friends;
	}

	public friends: string[] = [];
}
