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
public class Note {
	private String note;
	private String noteable_type;
	private String noteable_id;
	private String hook_name;
	private String password;
	private String url;

	private Project project;
	private Repository repository;
	private User author;

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNoteable_type() {
		return noteable_type;
	}

	public void setNoteable_type(String noteable_type) {
		this.noteable_type = noteable_type;
	}

	public String getNoteable_id() {
		return noteable_id;
	}

	public void setNoteable_id(String noteable_id) {
		this.noteable_id = noteable_id;
	}

	public String getHook_name() {
		return hook_name;
	}

	public void setHook_name(String hook_name) {
		this.hook_name = hook_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
