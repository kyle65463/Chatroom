export abstract class Message {
	public static parse(message: MessageEvent<any>): Message {
        return new LoginSuccessMessage();
    }
}

export class LoginSuccessMessage extends Message {

}
