package fr.upem.factory;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.service.CommitService;

public class CommitGitFactoryKit {
	/**
	 * 
	 * This function return all commit from github repository where given in parameter
	 * @param repoName name of repository
	 * @param username username github
	 * @return list of commit
	 * @throws IOException
	 */
	public static List<String> getCommit(String repoName,String username) throws IOException {
		List<String> ret = new ArrayList<>();
		final int size = 25;
		final RepositoryId repo = new RepositoryId(username, repoName);
		final String message = "  by {0} : {1} on {2}";
		final CommitService service = new CommitService();
		int pages = 1;
		for (Collection<RepositoryCommit> commits : service.pageCommits(repo,
				size)) {
			System.out.println("Commit Page " + pages++);
			for (RepositoryCommit commit : commits) {
				//String sha = commit.getSha().substring(0, 7);
				String m = commit.getCommit().getMessage();
				String author = commit.getCommit().getAuthor().getName();
				Date date = commit.getCommit().getAuthor().getDate();
				ret.add(MessageFormat.format(message, author, m,
						date));
			}
		}
		return ret;
	}
}
