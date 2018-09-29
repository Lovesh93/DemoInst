package com.insta.organic;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.InstagramFollowRequest;
import org.brunocvcunha.instagram4j.requests.InstagramGetUserFollowersRequest;
import org.brunocvcunha.instagram4j.requests.InstagramLikeRequest;
import org.brunocvcunha.instagram4j.requests.InstagramPostCommentRequest;
import org.brunocvcunha.instagram4j.requests.InstagramSearchUsernameRequest;
import org.brunocvcunha.instagram4j.requests.InstagramUnfollowRequest;
import org.brunocvcunha.instagram4j.requests.InstagramUnlikeRequest;
import org.brunocvcunha.instagram4j.requests.InstagramUserFeedRequest;
import org.brunocvcunha.instagram4j.requests.payload.InstagramFeedItem;
import org.brunocvcunha.instagram4j.requests.payload.InstagramFeedResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramGetUserFollowersResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramProfilePic;
import org.brunocvcunha.instagram4j.requests.payload.InstagramSearchUsernameResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUserSummary;

/**
 * Hello world!
 *
 */
public class App {
	private static String username = "insta.dev.organic";
	private static String password = "9929496321";
	private static Instagram4j instagram;

	public static void main(String[] args) {
		// login to instagram
		instagram = loginToInstagram(username, password);
		System.out.println("userId: " + instagram.getUserId());
		System.out.println("username: " + instagram.getUsername());

		String serachHandle = "sumit__suthar";

		// get searched user name result
		InstagramSearchUsernameResult userResult = searchUserByHandle(serachHandle);

		System.out.println("ID for " + serachHandle + " is " + userResult.getUser().getPk());
		System.out.println("Number of followers: " + userResult.getUser().getFollower_count());
		System.out.println("Number of followings: " + userResult.getUser().getFollowing_count());
		System.out.println("Media count: " + userResult.getUser().geo_media_count);
		System.out.println("Biography: " + userResult.getUser().getBiography());
		System.out.println("complete user result:" + userResult.getUser());
		List<InstagramProfilePic> profilepics = userResult.getUser().getHd_profile_pic_versions();
		System.out.println("All hd profile pics");
		for (InstagramProfilePic profilepic : profilepics) {
			System.out.println(profilepic.getUrl());
		}

		// this will give you user followers if the profile is public or you are
		// following the given user
		List<InstagramUserSummary> users = getUserFollowers(userResult.getUser().getPk());

		for (InstagramUserSummary user : users) {
			System.out.println("User " + user.getUsername());
			System.out.println("User full name:" + user.getFull_name());
			System.out.println("User profile pic url:" + user.getProfile_pic_url());
		}

		// unfollow user
		unfollowUser(userResult.getUser().getPk());

		// follow user
		followUser(userResult.getUser().getPk());

		// get user media feeds,needs to be implemented
		InstagramFeedResult userMediaFeeds = getUserFeeds(userResult.getUser().getPk());
		System.out.println("user getNum_results: " + userMediaFeeds.getNum_results());
		System.out.println("userMediaFeeds" + userMediaFeeds);

		List<InstagramFeedItem> items = userMediaFeeds.getItems();
		System.out.println("items size:" + items.size());
		for (InstagramFeedItem item : items) {
			System.out.println(item.getPk());
			Long itemPk = item.getPk();

			// unlike media
			 unlikeMedia(itemPk);

			// like media
			 likeMedia(itemPk);

			// post a comment
//			postCommentForMedia(itemPk, "Nice one...");

		}
		
		//get self timline media feeds
		getTimelineMediafeeds();

	}

	private static void getTimelineMediafeeds() {
//		InstagramFeedResult userMediaFeeds = null;
//		userMediaFeeds = instagram.sendRequest(new InstagramTimelineFeedRequest());
	}

	private static void postCommentForMedia(Long itemPk, String comment) {
		try {
			instagram.sendRequest(new InstagramPostCommentRequest(itemPk, comment));
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void unlikeMedia(Long itemPk) {
		try {
			instagram.sendRequest(new InstagramUnlikeRequest(itemPk));
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void likeMedia(Long itemPk) {
		try {
			instagram.sendRequest(new InstagramLikeRequest(itemPk));
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void unfollowUser(long pk) {
		try {
			instagram.sendRequest(new InstagramUnfollowRequest(pk));
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void followUser(long pk) {
		try {
			instagram.sendRequest(new InstagramFollowRequest(pk));
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static InstagramFeedResult getUserFeeds(long userId) {
		// TODO need to work on it
		InstagramFeedResult userMediaFeeds = null;
		try {
			userMediaFeeds = instagram.sendRequest(new InstagramUserFeedRequest(userId));
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return userMediaFeeds;

	}

	private static List<InstagramUserSummary> getUserFollowers(long pk) {
		InstagramGetUserFollowersResult followers = null;
		try {
			followers = instagram.sendRequest(new InstagramGetUserFollowersRequest(pk));
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<InstagramUserSummary> users = followers.getUsers();
		return users;
	}

	private static InstagramSearchUsernameResult searchUserByHandle(String handle) {

		InstagramSearchUsernameResult userResult = null;
		try {
			userResult = instagram.sendRequest(new InstagramSearchUsernameRequest(handle));
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userResult;
	}

	private static Instagram4j loginToInstagram(String username2, String password2) {
		// Login to instagram
		Instagram4j instagram = Instagram4j.builder().username(username).password(password).build();
		instagram.setup();
		try {
			instagram.login();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(instagram);
		return instagram;
	}
}