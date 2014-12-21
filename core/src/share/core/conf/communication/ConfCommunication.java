package share.core.conf.communication;

public class ConfCommunication
{
	public String local = "";
	public String remote = "";

	public String getIp(boolean isLocal)
	{
		String[] parts = (isLocal) ? this.local.split(":") : this.remote.split(":");

		return (parts.length > 0) ? parts[0] : "";
	}

	public int getPort(boolean isLocal)
	{
		String[] parts = (isLocal) ? this.local.split(":") : this.remote.split(":");

		return (parts.length > 1) ? Integer.parseInt(parts[1]) : 0;
	}
}