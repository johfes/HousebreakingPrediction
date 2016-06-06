package biz.fesenmeyer;

public class Housebreaking {
	 boolean doorKickedIn;
	 boolean moneyStolen;
	 boolean jewelryStolen;
	 boolean mobileStolen;
	 boolean otherThingsStolen;
	 String location;
	 
	public Housebreaking(boolean doorKickedIn, boolean moneyStolen,
			boolean jewelryStolen, boolean mobileStolen,
			boolean otherThingsStolen, String location) {
		super();
		this.doorKickedIn = doorKickedIn;
		this.moneyStolen = moneyStolen;
		this.jewelryStolen = jewelryStolen;
		this.mobileStolen = mobileStolen;
		this.otherThingsStolen = otherThingsStolen;
		this.location = location;
	}

	public boolean isDoorKickedIn() {
		return doorKickedIn;
	}

	public boolean isMoneyStolen() {
		return moneyStolen;
	}

	public boolean isJewelryStolen() {
		return jewelryStolen;
	}

	public boolean isMobileStolen() {
		return mobileStolen;
	}

	public boolean isOtherThingsStolen() {
		return otherThingsStolen;
	}

	public String getLocation() {
		return location;
	}

	@Override
	public String toString() {
		return "Housebreaking \n"
				+"[doorKickedIn=" + doorKickedIn + ", moneyStolen="
				+ moneyStolen + ", \n jewelryStolen=" + jewelryStolen
				+ ", mobileStolen=" + mobileStolen + ",\n otherThingsStolen="
				+ otherThingsStolen + ", location=" + location + "]";
	}

}



