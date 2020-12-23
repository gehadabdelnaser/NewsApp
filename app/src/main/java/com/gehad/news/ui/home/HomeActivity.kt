package com.gehad.news.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gehad.news.R
import com.gehad.news.adapter.NewsAdapter
import com.gehad.news.data.ArticlesItem
import com.gehad.news.data.SourcesItem
import com.gehad.news.model.Constant
import com.gehad.news.ui.NewsDetailsActivity
import com.google.android.material.tabs.TabLayout
import com.gehad.base.BaseActivity
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : BaseActivity() ,TabLayout.OnTabSelectedListener {

    private var adapter= NewsAdapter(null)
    private var source:SourcesItem?=null
    private var search:String?=null
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        viewModel=ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.getNewsSources()
        observeLiveData()
        news_recycler_view.adapter=adapter

        adapter.onItemClickListener=object :NewsAdapter.OnItemClickListener{
            override fun onItemClick(position: Int, news: ArticlesItem?) {
                val intent=Intent(activity, NewsDetailsActivity::class.java)
                intent.putExtra(Constant.NEWS ,news)
                startActivity(intent)
            }
        }
    }

    private fun observeLiveData(){
        viewModel.sourcesLiveData.observe(this, Observer {
            setUpTabLayout(it)
        })
        viewModel.showLoadingLiveData.observe(this, Observer {
            if (it == false){
                progress_bar.visibility= View.GONE
            }
        })
        viewModel.showMassageLiveData.observe(this, Observer {
            showMassage(null,it,null,null,null,null,true)
        })
    }
    private fun setUpTabLayout(sources: List<SourcesItem?>?) {
        for (source in sources.orEmpty()) {
            val tab = tab_layout.newTab()
            tab.text=source?.name
            //put pointer on object in tab
            tab.tag = source
            tab_layout.addTab(tab)
        }
        tab_layout.addOnTabSelectedListener(this)
        tab_layout.getTabAt(0)?.select()
    }
    override fun onTabReselected(tab: TabLayout.Tab?) {
        //refrash
        source = tab?.tag as SourcesItem
        getNews(null)

    }
    override fun onTabUnselected(tab: TabLayout.Tab?) {

    }
    override fun onTabSelected(tab: TabLayout.Tab?) {
        source = tab?.tag as SourcesItem
        getNews(null)

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        val searchItem=menu?.findItem(R.id.app_bar_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.queryHint="Search Here!"
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                search=newText
                getNews(search)
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun getNews(word:String?){

        viewModel.getNewsBySourceId(source!!.id?:"",word)
        viewModel.newsLiveData.observe(this, Observer {
            adapter.changeData(it)
        })
        news_recycler_view.layoutManager?.scrollToPosition(0)

    }
}
