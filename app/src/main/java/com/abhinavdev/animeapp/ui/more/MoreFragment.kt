package com.abhinavdev.animeapp.ui.more

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.core.BaseFragment
import com.abhinavdev.animeapp.databinding.DialogLoginBinding
import com.abhinavdev.animeapp.databinding.DialogOptionsBinding
import com.abhinavdev.animeapp.databinding.FragmentMoreBinding
import com.abhinavdev.animeapp.databinding.InflationLoaderLayoutBinding
import com.abhinavdev.animeapp.ui.common.listeners.OnClickMultiTypeCallback
import com.abhinavdev.animeapp.ui.main.MainActivity
import com.abhinavdev.animeapp.ui.models.ItemSelectionModelBase
import com.abhinavdev.animeapp.ui.more.adapters.ItemSelectionAdapter
import com.abhinavdev.animeapp.ui.more.adapters.setOptionSelected
import com.abhinavdev.animeapp.ui.more.misc.SettingsItemSelectionType
import com.abhinavdev.animeapp.util.Const
import com.abhinavdev.animeapp.util.PrefUtils
import com.abhinavdev.animeapp.util.appsettings.AppLanguage
import com.abhinavdev.animeapp.util.appsettings.AppTheme
import com.abhinavdev.animeapp.util.appsettings.AppTitleType
import com.abhinavdev.animeapp.util.appsettings.SettingsHelper
import com.abhinavdev.animeapp.util.extension.ViewUtil
import com.abhinavdev.animeapp.util.extension.clickable
import com.abhinavdev.animeapp.util.extension.hide
import com.abhinavdev.animeapp.util.extension.inflateLayoutAsync
import com.abhinavdev.animeapp.util.extension.setStatusBarColorAsTheme
import com.abhinavdev.animeapp.util.extension.setTheme
import com.abhinavdev.animeapp.util.extension.setThemeChangeAnimation
import com.abhinavdev.animeapp.util.extension.showOrHide
import com.abhinavdev.animeapp.util.ui.LoginUtil.showLoginDialog
import com.google.android.material.bottomsheet.BottomSheetDialog

class MoreFragment : BaseFragment(), View.OnClickListener, OnClickMultiTypeCallback {
    private var _binding: FragmentMoreBinding? = null
    private val binding get() = _binding!!
    private var parentActivity: MainActivity? = null

    private var languageList: List<ItemSelectionModelBase> = arrayListOf()
    private var titleTypeList: List<ItemSelectionModelBase> = arrayListOf()
    private var themeList: List<ItemSelectionModelBase> = arrayListOf()

    private var optionAdapter: ItemSelectionAdapter<SettingsItemSelectionType>? = null
    private var optionBottomSheetDialog: BottomSheetDialog? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentActivity = context as MainActivity
    }

    override fun onDetach() {
        super.onDetach()
        parentActivity = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val loaderBinding = InflationLoaderLayoutBinding.inflate(inflater, container, false)

        inflater.inflateLayoutAsync(R.layout.fragment_more, container) {
            _binding = FragmentMoreBinding.bind(it)
            (loaderBinding.root as? ViewGroup)?.addView(binding.root)
            init()
        }
        return loaderBinding.root
    }

    private fun init() {
        initComponents()
        setAdapters()
        setListeners()
        //set layout first time we arrive on fragment
        setAuthLayout(PrefUtils.getBoolean(Const.PrefKeys.IS_AUTHENTICATED_KEY))
    }

    private fun initComponents() {
        languageList = AppLanguage.list.map {
            ItemSelectionModelBase(it.search, it.showName).apply {
                isSelected = SettingsHelper.getAppLanguage() == it
            }
        }
        titleTypeList = AppTitleType.list.map {
            ItemSelectionModelBase(it.search, it.showName).apply {
                isSelected = SettingsHelper.getPreferredTitleType() == it
            }
        }
        themeList = AppTheme.list.map {
            ItemSelectionModelBase(it.search, getString(it.stringRes)).apply {
                isSelected = SettingsHelper.getAppTheme() == it
            }
        }
        with(binding.toolbar) {
            ivBack.hide()
            tvTitle.text = getString(R.string.msg_more)

            ViewUtil.setOnApplyUiInsetsListener(root) { insets ->
                ViewUtil.setTopPadding(root, insets.top)
            }
        }

        //setting clickable from xml is not working
        //if switch is non clickable then user where ever clicks only linearlayout will be triggered
        binding.groupSfw.switchItem.clickable(false)
        val theme = SettingsHelper.getAppTheme().stringRes
        val language = SettingsHelper.getAppLanguage().showName
        val titleType = SettingsHelper.getPreferredTitleType().showName
        val enableSfw = SettingsHelper.getSfwEnabled()

        with(binding.groupMyProfile) {
            tvTitle.text = getString(R.string.msg_my_profile)
            tvDescription.text = getString(R.string.msg_mal_profile_decription)
            ivStartIcon.setImageResource(R.drawable.ic_profile)
        }
        with(binding.groupMyAnime) {
            tvTitle.text = getString(R.string.msg_my_anime_list)
            tvDescription.text = getString(R.string.msg_my_anime_description)
            ivStartIcon.setImageResource(R.drawable.ic_anime_list)
        }
        with(binding.groupMyManga) {
            tvTitle.text = getString(R.string.msg_my_manga_list)
            tvDescription.text = getString(R.string.msg_my_manga_description)
            ivStartIcon.setImageResource(R.drawable.ic_manga_list)
        }
        with(binding.groupAppTheme) {
            tvTitle.text = getString(R.string.msg_app_theme)
            tvDescription.text = getString(R.string.msg_theme_description)
            ivStartIcon.setImageResource(R.drawable.ic_day)
            tvHint.text = getString(theme)
        }
        with(binding.groupTitleType) {
            tvTitle.text = getString(R.string.msg_title_language)
            tvDescription.text = getString(R.string.msg_title_lang_description)
            ivStartIcon.setImageResource(R.drawable.ic_language)
            tvHint.text = titleType
        }
        with(binding.groupAppLanguage) {
            tvTitle.text = getString(R.string.msg_language)
            tvDescription.text = getString(R.string.msg_language_description)
            ivStartIcon.setImageResource(R.drawable.ic_global_language)
            tvHint.text = language
        }
        with(binding.groupSfw) {
            tvTitle.text = getString(R.string.msg_sfw)
            tvDescription.text = getString(R.string.msg_sfw_description)
            ivStartIcon.setImageResource(R.drawable.ic_sfw)
            switchItem.setChecked(enableSfw)
        }
    }

    private fun setAuthLayout(authenticated: Boolean) {
        with(binding) {
            llLogout.showOrHide(authenticated)
            viewLogout.showOrHide(authenticated)
            flLoginLayer.showOrHide(!authenticated)
        }
    }

    override fun onResume() {
        super.onResume()
        //set layout when while being in fragment value changes
        PrefUtils.setBooleanObserver(Const.PrefKeys.IS_AUTHENTICATED_KEY) {
            setAuthLayout(it)
        }
    }

    override fun onPause() {
        super.onPause()
        PrefUtils.removeObserver()
    }

    private fun setAdapters() {

    }

    private fun setListeners() {
        binding.groupMyProfile.llItem.setOnClickListener(this)
        binding.groupMyAnime.llItem.setOnClickListener(this)
        binding.groupMyManga.llItem.setOnClickListener(this)
        binding.groupAppTheme.llItem.setOnClickListener(this)
        binding.groupTitleType.llItem.setOnClickListener(this)
        binding.groupAppLanguage.llItem.setOnClickListener(this)
        binding.groupSfw.llItem.setOnClickListener(this)
        binding.llLogout.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.groupMyProfile.llItem -> parentActivity?.navigateToFragment(ProfileFragment.newInstance())
            binding.groupMyAnime.llItem -> parentActivity?.navigateToFragment(MyAnimeListFragment.newInstance())
            binding.groupMyManga.llItem -> parentActivity?.navigateToFragment(MyMangaListFragment.newInstance())
            binding.groupAppTheme.llItem -> openOptionDialog(
                themeList, SettingsItemSelectionType.THEME
            )

            binding.groupTitleType.llItem -> openOptionDialog(
                titleTypeList, SettingsItemSelectionType.TITLE_LANGUAGE
            )

            binding.groupAppLanguage.llItem -> openOptionDialog(
                languageList, SettingsItemSelectionType.APP_LANGUAGE
            )

            binding.groupSfw.llItem -> onSfwClick()
            binding.llLogout -> onLogoutClick()
            binding.btnLogin -> context?.showLoginDialog()
        }
    }

    private fun openOptionDialog(
        list: List<ItemSelectionModelBase>, type: SettingsItemSelectionType
    ) {
        optionBottomSheetDialog =
            BottomSheetDialog(requireContext(), R.style.NoBackGroundBottomSheetDialog)
        val view = DialogOptionsBinding.inflate(layoutInflater)

        with(view) {
            val title = when (type) {
                SettingsItemSelectionType.APP_LANGUAGE -> {
                    R.string.msg_choose_app_language
                }

                SettingsItemSelectionType.TITLE_LANGUAGE -> {
                    R.string.msg_choose_title_language
                }

                SettingsItemSelectionType.THEME -> {
                    R.string.msg_choose_app_theme
                }
            }
            tvTitle.text = getString(title)
            optionAdapter = ItemSelectionAdapter(list, this@MoreFragment, type)
            rvItems.setHasFixedSize(true)
            rvItems.layoutManager = LinearLayoutManager(context)
            rvItems.adapter = optionAdapter
        }

        optionBottomSheetDialog?.setContentView(view.root)
        optionBottomSheetDialog?.show()
    }

    private fun onSfwClick() {
        val isChecked = binding.groupSfw.switchItem.isChecked
        binding.groupSfw.switchItem.setChecked(!isChecked, true)
        PrefUtils.setBoolean(Const.PrefKeys.SFW_ENABLE_KEY, !isChecked)
    }

    private fun onLogoutClick() {
        val dialog = BottomSheetDialog(requireContext(), R.style.NoBackGroundBottomSheetDialog)
        val view = DialogLoginBinding.inflate(layoutInflater)

        with(view) {
            tvTitle.text = getString(R.string.msg_logout)
            tvDescription.text = getString(R.string.msg_logout_des)
            btnNegative.text = getString(R.string.msg_cancel)
            btnPositive.text = getString(R.string.msg_okay)
            checkbox.hide()
        }

        view.btnNegative.setOnClickListener {
            dialog.cancel()
        }
        view.btnPositive.setOnClickListener {
            dialog.cancel()
            parentActivity?.logout()
        }
        dialog.setContentView(view.root)
        dialog.show()
    }

    override fun <T> onItemClick(position: Int, type: T) {
        when (type as SettingsItemSelectionType) {
            SettingsItemSelectionType.APP_LANGUAGE -> {
                languageList.setOptionSelected(position) {
                    binding.groupAppLanguage.tvHint.text = it.name
                    runCommonOptionFunction()
                    PrefUtils.setString(Const.PrefKeys.APP_LANGUAGE_KEY, it.id)
                    parentActivity?.navigateToHome()
                }
            }

            SettingsItemSelectionType.TITLE_LANGUAGE -> {
                titleTypeList.setOptionSelected(position) {
                    binding.groupTitleType.tvHint.text = it.name
                    runCommonOptionFunction()
                    PrefUtils.setString(Const.PrefKeys.PREFERRED_TITLE_TYPE_KEY, it.id)
                    parentActivity?.navigateToHome()
                }
            }

            SettingsItemSelectionType.THEME -> {
                themeList.setOptionSelected(position) {
                    binding.groupAppTheme.tvHint.text = it.name
                    runCommonOptionFunction()
                    PrefUtils.setString(Const.PrefKeys.APP_THEME_KEY, it.id)
                    val theme = AppTheme.valueOfOrDefault(it.id)
                    activity?.setThemeChangeAnimation()
                    setTheme(theme)
                    activity?.setStatusBarColorAsTheme(theme)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun runCommonOptionFunction() {
        optionAdapter?.notifyDataSetChanged()
        optionBottomSheetDialog?.cancel()
    }

    companion object {
        @JvmStatic
        fun newInstance() = MoreFragment()
    }
}