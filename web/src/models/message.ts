export abstract class Message {
	public static loginParse(data:string): Message{
		let index:number = data.indexOf("OK");
		if(index == -1){
			console.log("login failed");
			return new LoginFailedMessage();
		}
		let authIndex:number = data.indexOf("authToken");
		let authToken:string = "";
		for(let i:number = authIndex + 12;i < data.length;i++){
			if(data[i] == "\""){
				break;
			}
			authToken += data[i];
		}
		console.log(authToken);
		return new LoginSuccessMessage(authToken);
	}

	public static parse(message: MessageEvent<any>): Message {
		// Classify what message it is
		// Parse
		let data:string = message.data;
		let path_Index:number = data.indexOf("Path: /");
		let path:string = "";
		for(let i:number = path_Index + 7;i < data.length;i++){
			if(data[i] == "\r"){
				break;
			}
			path += data[i];
		}
		if(path == "login"){
			return Message.loginParse(data);
		}
		console.log(path);
		let line:string;
		return new ListFriendMessage(["luca"]);
	}

}

export class LoginSuccessMessage extends Message {
	constructor(authToken: string) {
		super();
		this.authToken = authToken;
	}
	public authToken:string;
}

export class LoginFailedMessage extends Message {}


export class ListFriendMessage extends Message {
	constructor(friends: string[]) {
		super();
		this.friends = friends;
	}

	public friends: string[] = [];
}
