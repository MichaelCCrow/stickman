package entities.weapons;

public enum Gun {
	
	UNARMED, PISTOL, RIFLE, BUTTER, GAMEBREAKER;
	
	private static Gun[] vals = values();
	
	public Gun next()
    {
        return vals[(this.ordinal()+1) % vals.length];
    }

}
