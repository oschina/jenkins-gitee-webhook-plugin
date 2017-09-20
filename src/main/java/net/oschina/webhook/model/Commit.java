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
public class Commit {
    private Committer committer;
    private String web_url;
    private String short_message;
    private String sha;
    
    private String id;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Committer getCommitter() {
		return committer;
	}
	public void setCommitter(Committer committer) {
		this.committer = committer;
	}
	public String getWeb_url() {
		return web_url;
	}
	public void setWeb_url(String web_url) {
		this.web_url = web_url;
	}
	public String getShort_message() {
		return short_message;
	}
	public void setShort_message(String short_message) {
		this.short_message = short_message;
	}
	public String getSha() {
		return sha;
	}
	public void setSha(String sha) {
		this.sha = sha;
	}
}
