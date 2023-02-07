package com.example.network2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RepoViewHolder(view: View): RecyclerView.ViewHolder(view){
    val repoName: TextView
    init {
        repoName = view.findViewById(R.id.repo_id)
    }
}

class RepoAdapter(val repoResults: RepoResult): RecyclerView.Adapter<RepoViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val repoView = LayoutInflater.from(
            parent.context).inflate(R.layout.repo_item, parent, false)
        return RepoViewHolder(repoView)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.repoName.text = repoResults[position].name
    }

    override fun getItemCount(): Int {
        return repoResults.size
    }

}