#include "stdafx.h"
#include <ObjBase.h>
#include <string>
#include <iostream>
using namespace std;

// �^�C�v���C�u�����C���|�[�g
// �i�^�C�v���C�u�����̓o�^�́A�yCeVIO Creative Studio�z�C���X�g�[�����ɍs���܂��B�j
#import "libid:D3AEA482-B527-4818-8CEA-810AFFCB24B6" named_guids rename_namespace("CeVIO")

int main(int argc, char *argv[])
{
	// COM������
	::CoInitialize(NULL);

	// ServiceControl�C���X�^���X����
	CeVIO::IServiceControl *pServiceControl;
	HRESULT result0 = ::CoCreateInstance(CeVIO::CLSID_ServiceControl,
		NULL,
		CLSCTX_INPROC_SERVER,
		CeVIO::IID_IServiceControl,
		reinterpret_cast<LPVOID *>(&pServiceControl));
	if (FAILED(result0)) {
		// ���s
		::CoUninitialize();
		return 0;
	}

	// ServiceControl�̃o�[�W����4.0�ǉ��@�\�C���^�[�t�F�C�X���擾
	CeVIO::IServiceControlV40Part *pServiceControlV40Part = NULL;
	HRESULT result = pServiceControl->QueryInterface(CeVIO::IID_IServiceControlV40Part,
		reinterpret_cast<LPVOID *>(&pServiceControlV40Part));
	if (SUCCEEDED(result)) {
		//MessageBox(NULL, pServiceControlV40Part->HostVersion.GetBSTR(), L"", MB_OK);
	}
	else {
		// �o�[�W����4.0�ȑO�̏ꍇ�͂�����ɕ���
	}

	// �yCeVIO Creative Studio�z�N��
	pServiceControl->StartHost(false);

	// Talker�C���X�^���X����
	CeVIO::ITalker *pTalker;
	HRESULT result1 = ::CoCreateInstance(CeVIO::CLSID_Talker,
		NULL,
		CLSCTX_INPROC_SERVER,
		CeVIO::IID_ITalker,
		reinterpret_cast<LPVOID *>(&pTalker));
	if (FAILED(result1)) {
		// ���s
		::CoUninitialize();
		return 0;
	}

	int count;
	for (count = 0; count < pTalker->AvailableCasts->Length; count++) {
		cout << pTalker->AvailableCasts->At(count) << endl;
	}

	

	// �L���X�g�ݒ�
	pTalker->Cast = "���Ƃ�������";

	// �i��j���ʐݒ�
	pTalker->Volume = 100;

	// ServiceControl�̃o�[�W����4.0�ǉ��@�\�C���^�[�t�F�C�X���擾
	CeVIO::ITalkerV40Part *pTalkerV40Part = NULL;
	result = pTalker->QueryInterface(CeVIO::IID_ITalkerV40Part,
		reinterpret_cast<LPVOID *>(&pTalkerV40Part));
	if (SUCCEEDED(result)) {
		// �i��j�}�g�ݒ�
		pTalkerV40Part->ToneScale = 100;
	}

	// �i��j�Đ�
	//CeVIO::ISpeakingStatePtr pState = pTalker->Speak("����ɂ���");
	//pState->Wait();

	// �i��j���f�f�[�^�擾
	CeVIO::IPhonemeDataArrayPtr pPhonemes = pTalker->GetPhonemes("�͂��߂܂���");

	// Talker����i�ǉ������j
	if (pTalkerV40Part != NULL) pTalkerV40Part->Release();

	// Talker���
	pTalker->Release();

	// �yCeVIO Creative Studio�z�I��
	pServiceControl->CloseHost(0);

	// ServiceControl����i�ǉ������j
	if (pServiceControlV40Part != NULL) pServiceControlV40Part->Release();

	// ServiceControl���
	pServiceControl->Release();

	// COM�g�p�I��
	::CoUninitialize();

	return 0;
}