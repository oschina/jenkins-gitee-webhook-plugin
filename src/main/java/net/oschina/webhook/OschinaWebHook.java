package net.oschina.webhook;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Iterator;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.jenkinsci.remoting.RoleChecker;
import org.kohsuke.stapler.HttpResponses;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import com.google.common.base.Splitter;
import com.google.gson.Gson;

import hudson.Extension;
import hudson.model.Item;
import hudson.model.ItemGroup;
import hudson.model.Job;
import hudson.model.UnprotectedRootAction;
import hudson.remoting.Callable;
import hudson.security.ACL;
import hudson.security.csrf.CrumbExclusion;
import jenkins.model.*;
import net.oschina.webhook.model.*;
@Extension
public class OschinaWebHook implements UnprotectedRootAction {
	public static final String WEBHOOK_URL = "oschina";
	public static final String API_TOKEN_PARAM = "private_token";

	@Override
	public String getIconFileName() {
		return null;
	}

	@Override
	public String getDisplayName() {
		return null;
	}

	@Override
	public String getUrlName() {
		return WEBHOOK_URL;
	}

	public void getDynamic(final String projectName, final StaplerRequest request, StaplerResponse response) {
		System.out.println("getDynamic.");
//		System.out.println(request.getRequestURIWithQueryString());
//		String method = request.getMethod();
//		System.out.println(method);
//		Iterator<String> restOfPathParts = Splitter.on('/').omitEmptyStrings().split(request.getRestOfPath())
//				.iterator();
//		Job<?, ?> project = resolveProject(projectName, restOfPathParts);
//		if (project == null) {
//			throw HttpResponses.notFound();
//		}
//
//		switch (method) {
//		case "POST":
//			String eventHeader = request.getHeader("X-Coding-Event");
//			System.out.println(eventHeader);
//			if (eventHeader == null) {
//				return;
//			}
//			if (StringUtils.equals(eventHeader, "ping")) {
//				return;
//			}
//			String json = getRequestBody(request);
//			System.out.println(json);
//			WebHook webHook = (WebHook) new Gson().fromJson(json, WebHook.class);
//			System.out.println(webHook);
//			ACL.impersonate(ACL.SYSTEM, () -> {
//
//			});
//			throw hudson.util.HttpResponses.ok();
//		case "GET":
//			System.out.println("GET");
//		default:
//			System.out.println("Default");
//			break;
//		}
	}

	private Job<?, ?> resolveProject(final String projectName, final Iterator<String> restOfPathParts) {
		return ACL.impersonate(ACL.SYSTEM, new Callable<Job<?, ?>, RuntimeException>() {
			@Override
			public Job<?, ?> call() throws RuntimeException {
				final Jenkins jenkins = Jenkins.getInstance();
				if (jenkins != null) {
					Item item = jenkins.getItemByFullName(projectName);
					while (item instanceof ItemGroup<?> && !(item instanceof Job<?, ?>) && restOfPathParts.hasNext()) {
						item = jenkins.getItem(restOfPathParts.next(), (ItemGroup<?>) item);
					}
					if (item instanceof Job<?, ?>) {
						return (Job<?, ?>) item;
					}
				}
				return null;
			}

			@Override
			public void checkRoles(RoleChecker checker) throws SecurityException {

			}
		});
	}

	private String getRequestBody(StaplerRequest request) {
		String requestBody;
		try {
			Charset charset = request.getCharacterEncoding() == null ? UTF_8
					: Charset.forName(request.getCharacterEncoding());
			requestBody = IOUtils.toString(request.getInputStream(), charset);
		} catch (IOException e) {
			throw HttpResponses.error(500, "Failed to read request body");
		}
		return requestBody;
	}

	@Extension
	public static class CodingWebHookCrumbExclusion extends CrumbExclusion {
		@Override
		public boolean process(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
				throws IOException, ServletException {
			String pathInfo = req.getPathInfo();
			System.out.println(pathInfo);
			chain.doFilter(req, resp);
			return true;
		}
	}
}
