package com.raka.trendinggitwithdaggerhilt.data.model.compact

data class ItemsCompact(val avatar_url:String,
                        val html_url:String,
                        val full_name: String,
                        val description: String,
                        val forks_count: Int,
                        val stargazers_count: Int,
                        val open_issues_count: Int)