package com.mitelcel.pack.dagger.component;

import com.mitelcel.pack.ui.fragment.FragmentAccount;
import com.mitelcel.pack.ui.fragment.FragmentCommunicate;
import com.mitelcel.pack.ui.fragment.FragmentConfirm;
import com.mitelcel.pack.ui.fragment.FragmentFrequentNumbers;
import com.mitelcel.pack.ui.fragment.FragmentHelp;
import com.mitelcel.pack.ui.fragment.FragmentLogin;
import com.mitelcel.pack.ui.fragment.FragmentLoginOrRegister;
import com.mitelcel.pack.ui.fragment.FragmentMain;
import com.mitelcel.pack.ui.fragment.FragmentOffers;
import com.mitelcel.pack.ui.fragment.FragmentPrivacy;
import com.mitelcel.pack.ui.fragment.FragmentRecent;
import com.mitelcel.pack.ui.fragment.FragmentRecharge;
import com.mitelcel.pack.ui.fragment.FragmentRegister;
import com.mitelcel.pack.ui.fragment.FragmentRequestPin;
import com.mitelcel.pack.ui.fragment.FragmentService;
import com.mitelcel.pack.ui.fragment.FragmentSplashScreen;
import com.mitelcel.pack.ui.fragment.FragmentTerms;
import com.mitelcel.pack.ui.fragment.FragmentTransfer;
import com.mitelcel.pack.ui.fragment.FragmentTutorialImage;
import com.mitelcel.pack.ui.fragment.FragmentTutorialText;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
public interface FragmentGraph {
    void inject(FragmentAccount fragmentAccount);
    void inject(FragmentConfirm fragmentConfirm);
    void inject(FragmentCommunicate fragmentCommunicate);
    void inject(FragmentFrequentNumbers fragmentFrequentNumbers);
    void inject(FragmentHelp fragmentHelp);
    void inject(FragmentLogin fragmentLogin);
    void inject(FragmentLoginOrRegister fragmentLoginOrRegister);
    void inject(FragmentMain fragmentMain);
    void inject(FragmentOffers fragmentOffers);
    void inject(FragmentPrivacy fragmentPrivacy);
    void inject(FragmentRecent fragmentRecent);
    void inject(FragmentRecharge fragmentRecharge);
    void inject(FragmentRegister fragmentRegister);
    void inject(FragmentRequestPin fragmentRequestPin);
    void inject(FragmentService fragmentService);
    void inject(FragmentSplashScreen fragmentSplashScreen);
    void inject(FragmentTransfer fragmentTransfer);
    void inject(FragmentTutorialImage fragmentTutorialImage);
    void inject(FragmentTutorialText fragmentTutorialText);
    void inject(FragmentTerms fragmentTerms);
}
