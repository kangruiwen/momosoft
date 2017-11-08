package main.socket.protocol.mydefine;
/**
 * @author momo
 * @time 2017年11月7日下午10:09:06
 * 
 * 该例子是一个简单的投票协议。这里，一个客户端向服务器发送一个请求消息，消息中包含了一个候选人的ID，范围在0~1000。
 * 程序支持两种请求：一种是查询请求，即向服务器询问候选人当前获得的投票总数，服务器发回一个响应消息，包含了原来的候选人ID和该候选人当前获得的选票总数；
 * 另一种是投票请求，即向指定候选人投一票，服务器对这种请求也发回响应消息，包含了候选人ID和获得的选票数（包含了刚刚投的一票）。
 * 
 * 在实现一个协议时，一般会定义一个专门的类来存放消息中所包含的的信息。在我们的例子中，客户端和服务端发送的消息都很简单，
 * 唯一的区别是服务端发送的消息还包含了选票总数和一个表示相应消息的标志。因此，可以用一个类来表示客户端和服务端的两种消息。
 * 
 * VoteMsg.java类展示了每条消息中的基本信息：
 * 	布尔值isInquiry，true表示该消息是查询请求，false表示该消息是投票请求；
 *  布尔值isResponse，true表示该消息是服务器发送的相应消息，false表示该消息为客户端发送的请求消息；
 *   整型变量candidateID,指示了候选人的ID；
 *   长整型变量voteCount，指示出所查询的候选人获得的总选票数。
 *  另外，注意一下几点：
 *   candidateID的范围在0~1000；
 *   voteCount在请求消息中必须为0；
 *   voteCount不能为负数
 */
public class VoteMsg {
	private boolean isInquiry; // true if inquiry; false if vote
	private boolean isResponse;// true if response from server
	private int candidateID; // in [0,1000]
	private long voteCount; // nonzero only in response

	public static final int MAX_CANDIDATE_ID = 1000;

	public VoteMsg(boolean isResponse, boolean isInquiry, int candidateID,
			long voteCount) throws IllegalArgumentException {
		// check invariants
		if (voteCount != 0 && !isResponse) {
			throw new IllegalArgumentException(
					"Request vote count must be zero");
		}
		if (candidateID < 0 || candidateID > MAX_CANDIDATE_ID) {
			throw new IllegalArgumentException("Bad Candidate ID: "
					+ candidateID);
		}
		if (voteCount < 0) {
			throw new IllegalArgumentException("Total must be >= zero");
		}
		this.candidateID = candidateID;
		this.isResponse = isResponse;
		this.isInquiry = isInquiry;
		this.voteCount = voteCount;
	}

	public void setInquiry(boolean isInquiry) {
		this.isInquiry = isInquiry;
	}

	public void setResponse(boolean isResponse) {
		this.isResponse = isResponse;
	}

	public boolean isInquiry() {
		return isInquiry;
	}

	public boolean isResponse() {
		return isResponse;
	}

	public void setCandidateID(int candidateID) throws IllegalArgumentException {
		if (candidateID < 0 || candidateID > MAX_CANDIDATE_ID) {
			throw new IllegalArgumentException("Bad Candidate ID: "
					+ candidateID);
		}
		this.candidateID = candidateID;
	}

	public int getCandidateID() {
		return candidateID;
	}

	public void setVoteCount(long count) {
		if ((count != 0 && !isResponse) || count < 0) {
			throw new IllegalArgumentException("Bad vote count");
		}
		voteCount = count;
	}

	public long getVoteCount() {
		return voteCount;
	}

	public String toString() {
		String res = (isInquiry ? "inquiry" : "vote") + " for candidate "
				+ candidateID;
		if (isResponse) {
			res = "response to " + res + " who now has " + voteCount
					+ " vote(s)";
		}
		return res;
	}

}
