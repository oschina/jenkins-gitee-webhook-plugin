package net.oschina.webhook.model;
/**
 * Copyright (C) 2017 ChengSong Hu <644340980@qq.com>
 * Copyright (C) 2016 Shuanglei Tao <tsl0922@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class Project {
	private String name;
	private String path;
	private String description;
	private String url;
	private String git_ssh_url;
	private String git_http_url;
	private String git_svn_url;
	private String namespace;
	private String name_with_namespace;
	private String path_with_namespace;
	private String default_branch;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getGit_ssh_url() {
		return git_ssh_url;
	}

	public void setGit_ssh_url(String git_ssh_url) {
		this.git_ssh_url = git_ssh_url;
	}

	public String getGit_http_url() {
		return git_http_url;
	}

	public void setGit_http_url(String git_http_url) {
		this.git_http_url = git_http_url;
	}

	public String getGit_svn_url() {
		return git_svn_url;
	}

	public void setGit_svn_url(String git_svn_url) {
		this.git_svn_url = git_svn_url;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getName_with_namespace() {
		return name_with_namespace;
	}

	public void setName_with_namespace(String name_with_namespace) {
		this.name_with_namespace = name_with_namespace;
	}

	public String getPath_with_namespace() {
		return path_with_namespace;
	}

	public void setPath_with_namespace(String path_with_namespace) {
		this.path_with_namespace = path_with_namespace;
	}

	public String getDefault_branch() {
		return default_branch;
	}

	public void setDefault_branch(String default_branch) {
		this.default_branch = default_branch;
	}

}
