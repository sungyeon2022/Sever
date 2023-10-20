package data;

import java.io.Serializable;
import java.util.Arrays;

import lombok.Data;

@Data
public class DataClass implements Serializable {
	private static final long serialVersionUID = 1L;
	/* 사용자 정보 */
	private String clientName;
	private int xPlayer;
	private int yPlayer;
	private int moveSpeed;
	private double attackDamage;
	private double life;
	private int intView;
	private boolean[] booleanView = new boolean[4];
	private boolean isAttack;
	private boolean isInvincible;
	
	/* 시스템 정보 */
	private String Timer;
	private boolean isStart;
	private boolean isReady;
	
	public DataClass() {}
	
//	@Override
//	public String toString() {
//		return "clientName : " + clientName + ", xPlayer : " + xPlayer + ", yPlayer : " + yPlayer 
//		+ ", moveSpeed : " + moveSpeed + ", attackDamage : " + attackDamage + ", life : " + life
//		+ ", intView : " + intView + ", booleanView : " + Arrays.toString(booleanView) + ", isAttack : "
//		+ isAttack + ", isInvincible : " + isInvincible + ", Timer : " + Timer + ", isStart : " + isStart
//		+ ", isReady : " + isReady;
//	}
	public void recivePlayerData(DataClass sendDataClass) {
		this.clientName = sendDataClass.clientName;
		this.xPlayer = sendDataClass.xPlayer;
		this.yPlayer = sendDataClass.yPlayer;
		this.moveSpeed = sendDataClass.moveSpeed;
		this.attackDamage = sendDataClass.attackDamage;
		this.life = sendDataClass.life;
		this.intView = sendDataClass.intView;
		this.booleanView = sendDataClass.booleanView;
		this.isAttack = sendDataClass.isAttack;
		this.isInvincible = sendDataClass.isInvincible;
	}
	public void reciveSystemData(DataClass sendDataClass) {
		this.Timer = sendDataClass.Timer;
		this.isStart = sendDataClass.isStart;
		this.isReady = sendDataClass.isReady;
	}
}
