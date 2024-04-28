package com.abhinavdev.animeapp.ui.more

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.core.BaseFragment
import com.abhinavdev.animeapp.databinding.DialogLoginBinding
import com.abhinavdev.animeapp.databinding.FragmentMoreBinding
import com.abhinavdev.animeapp.ui.main.MainActivity
import com.abhinavdev.animeapp.util.Const
import com.abhinavdev.animeapp.util.LoginUtil.showLoginDialog
import com.abhinavdev.animeapp.util.appsettings.PrefUtils
import com.abhinavdev.animeapp.util.appsettings.SettingsHelper
import com.abhinavdev.animeapp.util.extension.hide
import com.abhinavdev.animeapp.util.extension.nonClickable
import com.abhinavdev.animeapp.util.extension.showOrHide
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MoreFragment : BaseFragment(), View.OnClickListener {
    private var logoutDialog: AlertDialog? = null
    private var _binding: FragmentMoreBinding? = null
    private val binding get() = _binding!!
    private var parentActivity: MainActivity? = null

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
        _binding = FragmentMoreBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initComponents()
        setAdapters()
        setListeners()
        setObservers()
    }

    private fun initComponents() {
        //setting clickable from xml is not working
        binding.switchSfw.nonClickable()
        val theme = SettingsHelper.getAppTheme().stringRes
        val language = SettingsHelper.getAppLanguage().showName
        val titleType = SettingsHelper.getPreferredTitleType().showName
        val enableSfw = SettingsHelper.getSfwEnabled()

        binding.tvTheme.text = getString(theme)
        binding.tvLanguage.text = language
        binding.tvTitleType.text = titleType
        binding.switchSfw.setChecked(enableSfw)
    }

    private fun setAdapters() {

    }

    private fun setListeners() {
        binding.llMyProfile.setOnClickListener(this)
        binding.llMyAnimeList.setOnClickListener(this)
        binding.llMyMangaList.setOnClickListener(this)
        binding.llTheme.setOnClickListener(this)
        binding.llTitleType.setOnClickListener(this)
        binding.llLanguage.setOnClickListener(this)
        binding.llSfw.setOnClickListener(this)
        binding.llLogout.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.llMyProfile -> parentActivity?.navigateToFragment(ProfileFragment.newInstance())
            binding.llMyAnimeList -> {}
            binding.llMyMangaList -> {}
            binding.llTheme -> {}
            binding.llTitleType -> {}
            binding.llLanguage -> {}
            binding.llSfw -> onSfwClick()
            binding.llLogout -> onLogoutClick()
            binding.btnLogin -> context?.showLoginDialog()
        }
    }

    private fun onSfwClick() {
        val isChecked = binding.switchSfw.isChecked
        binding.switchSfw.setChecked(!isChecked,true)
        PrefUtils.setBoolean(Const.PrefKeys.SFW_ENABLE_KEY,!isChecked)
    }

    private fun onLogoutClick() {
        val builder = MaterialAlertDialogBuilder(requireContext())
        val view = DialogLoginBinding.inflate(layoutInflater)
        builder.setView(view.root)

        with(view) {
            tvTitle.text =  getString(R.string.msg_logout)
            tvDescription.text = getString(R.string.msg_logout_des)
            btnNegative.text =  getString(R.string.msg_cancel)
            btnPositive.text =  getString(R.string.msg_okay)
            checkbox.hide()
        }

        view.btnNegative.setOnClickListener {
            logoutDialog?.cancel()
        }
        view.btnPositive.setOnClickListener {
            logoutDialog?.cancel()
            parentActivity?.logout()
        }

        logoutDialog = builder.create()
        logoutDialog?.show()
    }

    private fun setObservers() {
        PrefUtils.onBooleanChange(Const.PrefKeys.IS_AUTHENTICATED_KEY) { authenticated ->
            binding.flLoginLayer.showOrHide(!authenticated)
            binding.viewStatusBar.showOrHide(!authenticated)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MoreFragment()
    }
}