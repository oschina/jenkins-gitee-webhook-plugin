package net.oschina.webhook.filter;

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
public class BranchFilterConfig {

	private BranchFilterType type;
    private String includeBranchesSpec;
    private String excludeBranchesSpec;
    private String targetBranchRegex;
    
    
    public BranchFilterConfig(BranchFilterType type){
    	this.type = type;
    }
    
    public BranchFilterConfig(BranchFilterType type,String includeBranchesSpec,
    		String excludeBranchesSpec,String targetBranchRegex){
    	this.type = type;
    	this.includeBranchesSpec = includeBranchesSpec;
    	this.excludeBranchesSpec = excludeBranchesSpec;
    	this.targetBranchRegex = targetBranchRegex;
    }
    
	public BranchFilterType getType() {
		return type;
	}
	public void setType(BranchFilterType type) {
		this.type = type;
	}
	public String getIncludeBranchesSpec() {
		return includeBranchesSpec;
	}
	public void setIncludeBranchesSpec(String includeBranchesSpec) {
		this.includeBranchesSpec = includeBranchesSpec;
	}
	public String getExcludeBranchesSpec() {
		return excludeBranchesSpec;
	}
	public void setExcludeBranchesSpec(String excludeBranchesSpec) {
		this.excludeBranchesSpec = excludeBranchesSpec;
	}
	public String getTargetBranchRegex() {
		return targetBranchRegex;
	}
	public void setTargetBranchRegex(String targetBranchRegex) {
		this.targetBranchRegex = targetBranchRegex;
	}
    
}
